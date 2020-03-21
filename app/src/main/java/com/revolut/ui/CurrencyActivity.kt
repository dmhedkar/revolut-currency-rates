package com.revolut.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.revolut.Injection
import com.revolut.R
import com.revolut.api.getApiConfig
import kotlinx.android.synthetic.main.activity_currency.*

class CurrencyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)
        list.setHasFixedSize(true)
        val currencyRatesAdapter = CurrencyRatesAdapter(emptyList())
        list.adapter = currencyRatesAdapter
        lifecycle.addObserver(CurrencyRateObserver(model) {
            currencyRatesAdapter.updateCurrencyRates(it)
        })
    }

    private val model: CurrencyRatesViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return CurrencyRatesViewModel(Injection.provideRepository(getApiConfig())) as T
            }
        }
    }

}

