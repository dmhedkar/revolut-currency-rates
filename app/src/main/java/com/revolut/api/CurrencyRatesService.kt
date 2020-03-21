package com.revolut.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CurrencyRatesService {
    @Headers("Content-Type:application/json")
    @GET("/api/android/latest")
    fun getCurrencyRates(@Query("base") currency: String): Observable<CurrencyRatesResponse>

    companion object {
        fun create(apiConfig: ApiConfig): CurrencyRatesService =
            ApiClient.init(apiConfig).getRetrofit().create(CurrencyRatesService::class.java)
    }
}