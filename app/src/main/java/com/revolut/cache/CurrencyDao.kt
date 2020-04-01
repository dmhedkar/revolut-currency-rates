package com.revolut.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Observable

@Dao
interface CurrencyDao {
    @Transaction
    fun updateCurrencyRate(currencyRate: List<CurrencyRateEntity>) {
        if (getCount() == 0) {
            insertCurrencyRates(currencyRate)
        } else {
            currencyRate.forEach {
                setCurrencyRate(it.currencyRate, it.currencyCode)
            }
        }
    }

    @Transaction
    fun reorderCurrencyRate(currencyRate: List<CurrencyRateEntity>) {
        clear()
        insertCurrencyRates(currencyRate)
    }

    @Query("SELECT COUNT(*) FROM CurrencyRateEntity")
    fun getCount(): Int

    @Query("update CurrencyRateEntity set `currencyRate` = :rate where `currencyCode`= :currencyCode")
    fun setCurrencyRate(rate: Double, currencyCode: String)

    @Insert
    fun insertCurrencyRates(list: List<CurrencyRateEntity>)

    @Query("Select * from CurrencyRateEntity")
    fun getCurrencies(): Observable<List<CurrencyRateEntity>>

    @Query("DELETE FROM CurrencyRateEntity")
    fun clear()
}