package com.revolut.repository

import com.revolut.api.CurrencyRatesService

class CurrencyRatesRepository(private val currencyRatesService: CurrencyRatesService) {
    fun fetchCurrencyRates(baseCurrency: String) =
        currencyRatesService.getCurrencyRates(baseCurrency)
}