package com.revolut

import com.revolut.api.ApiConfig
import com.revolut.api.CurrencyRatesService
import com.revolut.repository.CurrencyRatesRepository

object Injection {
    private fun provideService(apiConfig: ApiConfig) = CurrencyRatesService.create(apiConfig)
    fun provideRepository(apiConfig: ApiConfig) = CurrencyRatesRepository(provideService(apiConfig))
}