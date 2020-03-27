package com.revolut.api;

data class CurrencyRatesResponse(
    val baseCurrency: String,
    val rates: Rates
)