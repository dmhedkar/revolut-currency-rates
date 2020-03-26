package com.revolut.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.revolut.R
import com.revolut.di.factory.ViewModelFactory
import com.revolut.getCurrencyArray
import com.revolut.getItem
import com.revolut.moveToTop
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_currency.*
import javax.inject.Inject

class CurrencyActivity : AppCompatActivity() {
    lateinit var currencyRatesAdapter: CurrencyRatesAdapter
    lateinit var currencyRateObserver: CurrencyRateObserver

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val model: CurrencyRatesViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)
        list.setHasFixedSize(true)
        currencyRatesAdapter = CurrencyRatesAdapter {
            updateList(it)
        }
        list.adapter = currencyRatesAdapter
        currencyRateObserver = CurrencyRateObserver(model) {
            currencyRatesAdapter.updateCurrencyRates(it)
        }
        currencyRateObserver.baseCurrency = "EUR"
        lifecycle.addObserver(currencyRateObserver)
    }


    private fun updateList(index: Int) {
        currencyRateObserver.baseCurrency = getItem(index).currency
        moveToTop(index)
        currencyRatesAdapter.updateCurrencyRates(index, getCurrencyArray())
        list.scrollToPosition(0)
    }
}

