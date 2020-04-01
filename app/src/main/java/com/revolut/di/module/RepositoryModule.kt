package com.revolut.di.module

import com.revolut.api.CurrencyRatesService
import com.revolut.cache.CurrencyDao
import com.revolut.repository.CurrencyRatesRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideCurrencyRepository(
        currencyRatesService: CurrencyRatesService,
        currencyDao: CurrencyDao
    ) =
        CurrencyRatesRepository(currencyRatesService, currencyDao)

}