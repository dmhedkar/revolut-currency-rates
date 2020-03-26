package com.revolut.di.module

import com.revolut.api.CurrencyRatesService
import com.revolut.repository.CurrencyRatesRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideCurrencyRepository(currencyRatesService: CurrencyRatesService) =
        CurrencyRatesRepository(currencyRatesService)

}