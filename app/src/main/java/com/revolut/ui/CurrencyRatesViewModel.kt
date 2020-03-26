package com.revolut.ui

import androidx.lifecycle.ViewModel
import com.revolut.repository.CurrencyRatesRepository
import javax.inject.Inject

class CurrencyRatesViewModel @Inject constructor(private val repository: CurrencyRatesRepository) : ViewModel() {
    fun fetchCurrencyRates(baseCurrency: String) = repository.fetchCurrencyRates(baseCurrency)
}
