package com.revolut.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrencyRateEntity::class], version = 1, exportSchema = false)
abstract class StoreCache : RoomDatabase() {
    abstract fun currencyRatesDao(): CurrencyDao
}