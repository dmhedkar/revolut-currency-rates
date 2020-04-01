package com.revolut.di.module

import android.app.Application
import androidx.room.Room
import com.revolut.cache.CurrencyDao
import com.revolut.cache.StoreCache
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StoreModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application) =
        Room.inMemoryDatabaseBuilder(application, StoreCache::class.java)
            .allowMainThreadQueries()
            .build()

    @Provides
    @Singleton
    fun provideCurrencyRateDao(storeCache: StoreCache): CurrencyDao = storeCache.currencyRatesDao()
}