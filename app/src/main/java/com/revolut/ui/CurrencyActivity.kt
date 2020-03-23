package com.revolut.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.revolut.*
import com.revolut.api.getApiConfig
import kotlinx.android.synthetic.main.activity_currency.*

class CurrencyActivity : AppCompatActivity() {
    lateinit var currencyRatesAdapter: CurrencyRatesAdapter
    lateinit var currencyRateObserver: CurrencyRateObserver

    override fun onCreate(savedInstanceState: Bundle?) {
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

    private val model: CurrencyRatesViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CurrencyRatesViewModel(Injection.provideRepository(getApiConfig())) as T
            }
        }
    }
}

