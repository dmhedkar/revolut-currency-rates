package com.revolut.ui

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.revolut.api.CurrencyRatesResponse
import com.revolut.dto.CurrencyModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class CurrencyRateObserver constructor(
    private val model: CurrencyRatesViewModel,
    private val call: (List<CurrencyModel>) -> Unit
) :
    LifecycleObserver {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun fetchRates() {
        compositeDisposable.add(
            Observable.interval(
                    1L,
                    1L,
                    TimeUnit.SECONDS
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    compositeDisposable.add(
                        model.fetchCurrencyRates("EUR")
                            .retry(2)
                            .take(1)
                            .subscribeOn(Schedulers.from(Executors.newFixedThreadPool(5)))
                            .map {
                                return@map mapCurrencyList(it);
                            }
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                { list: List<CurrencyModel> ->
                                    call.invoke(list)

                                },
                                { t: Throwable ->
                                    t.printStackTrace()
                                })
                    )
                })
    }

    private fun mapCurrencyList(respo: CurrencyRatesResponse): List<CurrencyModel> {
        val mutableListOf = mutableListOf<CurrencyModel>()
        val muliplier = 100.0
        mutableListOf.add(
            CurrencyModel(
                "EUR",
                if (respo.rates.EUR == 0.0) muliplier else respo.rates.EUR
            )
        )
        mutableListOf.add(CurrencyModel("AUD", muliplier * respo.rates.AUD))
        mutableListOf.add(CurrencyModel("BGN", muliplier * respo.rates.BGN))
        mutableListOf.add(CurrencyModel("BRL", muliplier * respo.rates.BRL))
        mutableListOf.add(CurrencyModel("CAD", muliplier * respo.rates.CAD))
        mutableListOf.add(CurrencyModel("CHF", muliplier * respo.rates.CHF))
        mutableListOf.add(CurrencyModel("CNY", muliplier * respo.rates.CNY))
        mutableListOf.add(CurrencyModel("CZK", muliplier * respo.rates.CZK))
        mutableListOf.add(CurrencyModel("DKK", muliplier * respo.rates.DKK))
        mutableListOf.add(CurrencyModel("GBP", muliplier * respo.rates.GBP))
        mutableListOf.add(CurrencyModel("HKD", muliplier * respo.rates.HKD))
        mutableListOf.add(CurrencyModel("HRK", muliplier * respo.rates.HRK))
        mutableListOf.add(CurrencyModel("HUF", muliplier * respo.rates.HUF))
        mutableListOf.add(CurrencyModel("IDR", muliplier * respo.rates.IDR))
        mutableListOf.add(CurrencyModel("ILS", muliplier * respo.rates.ILS))
        mutableListOf.add(CurrencyModel("INR", muliplier * respo.rates.INR))
        mutableListOf.add(CurrencyModel("ISK", muliplier * respo.rates.ISK))
        mutableListOf.add(CurrencyModel("JPY", muliplier * respo.rates.JPY))
        mutableListOf.add(CurrencyModel("KRW", muliplier * respo.rates.KRW))
        mutableListOf.add(CurrencyModel("MXN", muliplier * respo.rates.MXN))
        mutableListOf.add(CurrencyModel("MYR", muliplier * respo.rates.MYR))
        mutableListOf.add(CurrencyModel("NOK", muliplier * respo.rates.NOK))
        mutableListOf.add(CurrencyModel("NZD", muliplier * respo.rates.NZD))
        mutableListOf.add(CurrencyModel("PHP", muliplier * respo.rates.PHP))
        mutableListOf.add(CurrencyModel("PLN", muliplier * respo.rates.PLN))
        mutableListOf.add(CurrencyModel("RON", muliplier * respo.rates.RON))
        mutableListOf.add(CurrencyModel("RUB", muliplier * respo.rates.RUB))
        mutableListOf.add(CurrencyModel("SEK", muliplier * respo.rates.SEK))
        mutableListOf.add(CurrencyModel("SGD", muliplier * respo.rates.SGD))
        mutableListOf.add(CurrencyModel("THB", muliplier * respo.rates.THB))
        mutableListOf.add(CurrencyModel("USD", muliplier * respo.rates.USD))
        return mutableListOf
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopFetchingRates() {
        compositeDisposable.clear()
    }
}