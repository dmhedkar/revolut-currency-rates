package com.revolut.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.revolut.dto.CurrencyModel
import com.revolut.mapCurrencyList
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CurrencyRateObserver constructor(
        private val model: CurrencyRatesViewModel,
        private val call: (List<CurrencyModel>) -> Unit
) :
        LifecycleObserver {
    lateinit var baseCurrency: String
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun fetchRates() {
        compositeDisposable.add(
                Observable.interval(0L, 1L, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .switchMap {
                            return@switchMap model.fetchCurrencyRates(baseCurrency)
                                    .map { return@map mapCurrencyList(it) }
                        }
                        .debounce(500, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { list: List<CurrencyModel> ->
                                    call.invoke(list)
                                },
                                { t: Throwable ->
                                    t.printStackTrace()
                                }))
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopFetchingRates() {
        compositeDisposable.clear()
    }
}