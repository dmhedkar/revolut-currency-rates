package com.revolut.repository

import com.revolut.api.CurrencyRatesService
import com.revolut.cache.CurrencyDao
import com.revolut.cache.CurrencyRateEntity
import com.revolut.currencyList
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrencyRatesRepository @Inject constructor(
    private val currencyRatesService: CurrencyRatesService,
    private val currencyDao: CurrencyDao
) {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val baseCurrency = BehaviorSubject.createDefault("EUR")

    val data = currencyDao.getCurrencies()

    fun getCurrencyRates() {
        compositeDisposable.add(
            Observable.combineLatest(
                Observable.interval(0L, 1L, TimeUnit.SECONDS),
                baseCurrency,
                BiFunction<Long, String, String> { t1, t2 -> t2 })
                .subscribeOn(Schedulers.io())
                .flatMap {
                    return@flatMap currencyRatesService.getCurrencyRates(baseCurrency.value!!)
                        .skipWhile { it.baseCurrency != baseCurrency.value!! }
                        .map { return@map currencyList(it) }
                }
                .toFlowable(BackpressureStrategy.LATEST)
                .debounce(1L, TimeUnit.MILLISECONDS)
                .subscribe({ list ->
                    currencyDao.updateCurrencyRate(list)
                }, {
                    //Handle error
                    it.printStackTrace()
                })
        )
    }

    fun cancel() {
        compositeDisposable.clear()
    }

    fun reorderList(list: List<CurrencyRateEntity>) {
        compositeDisposable.add(
            Single.fromCallable { currencyDao.reorderCurrencyRate(list) }
                .subscribeOn(Schedulers.io())
                .subscribe({
                    it?.let {
                        baseCurrency.onNext(list[0].currencyCode)
                    }
                }, {
                    it.printStackTrace()
                })
        )
    }
}