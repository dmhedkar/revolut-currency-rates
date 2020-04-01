package com.revolut.ui

import androidx.lifecycle.ViewModel
import com.revolut.cache.CurrencyRateEntity
import com.revolut.repository.CurrencyRatesRepository
import javax.inject.Inject

class CurrencyRatesViewModel @Inject constructor(
    private val repository: CurrencyRatesRepository
) : ViewModel() {
    val currencyRates = repository.data

    fun getCurrencyRates() {
        repository.getCurrencyRates()
    }

    fun stop() {
        repository.cancel()
    }

    fun reorderList(items: List<CurrencyRateEntity>) {
        repository.reorderList(items)
    }
}
