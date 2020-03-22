package com.revolut.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.revolut.Injection
import com.revolut.R
import com.revolut.api.getApiConfig
import com.revolut.dto.CurrencyModel
import com.revolut.getCurrencyArray
import kotlinx.android.synthetic.main.activity_currency.*

class CurrencyActivity : AppCompatActivity() {
    lateinit var currencyRatesAdapter: CurrencyRatesAdapter
    lateinit var currencyRateObserver: CurrencyRateObserver
    private var currentList = mutableListOf<CurrencyModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)
        list.setHasFixedSize(true)
        currencyRatesAdapter = CurrencyRatesAdapter(emptyArray()) {
            updateList(it)
        }
        list.adapter = currencyRatesAdapter
        currencyRateObserver = CurrencyRateObserver(model) {
            val arrayCurrency = getCurrencyArray(currentList, it)
            currentList = arrayCurrency.toMutableList()
            currencyRatesAdapter.updateCurrencyRates(arrayCurrency)
        }
        currencyRateObserver.baseCurrency = "EUR"
        lifecycle.addObserver(currencyRateObserver)
    }


    private fun updateList(it: Int) {
        val item = currentList.removeAt(it)
        currencyRateObserver.baseCurrency = item.currency
        currentList.add(0, item)
        currencyRatesAdapter.updateCurrencyRates(currentList.toTypedArray())
        currencyRatesAdapter.notifyItemMoved(it, 0)
        currencyRatesAdapter.notifyItemRangeChanged(1, currentList.size)
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

