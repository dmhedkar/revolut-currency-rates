package com.revolut.dto

data class CurrencyModel(val currency: String, var rate: Double = 1.0, val currencyText: String)