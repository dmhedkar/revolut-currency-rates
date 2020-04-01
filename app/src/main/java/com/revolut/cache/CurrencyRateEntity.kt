package com.revolut.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class CurrencyRateEntity(
    @PrimaryKey val currencyCode: String,
    var currencyRate: Double,
    val currencyName: String
)