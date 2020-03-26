package com.revolut.repository

import com.revolut.api.CurrencyRatesService
import javax.inject.Inject

class CurrencyRatesRepository @Inject constructor(private val currencyRatesService: CurrencyRatesService) {
    fun fetchCurrencyRates(baseCurrency: String) =
        currencyRatesService.getCurrencyRates(baseCurrency)
}