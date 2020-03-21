package com.revolut.ui

import androidx.lifecycle.ViewModel
import com.revolut.repository.CurrencyRatesRepository

class CurrencyRatesViewModel(private val repository: CurrencyRatesRepository) : ViewModel() {
    fun fetchCurrencyRates(baseCurrency: String) = repository.fetchCurrencyRates(baseCurrency)
}
