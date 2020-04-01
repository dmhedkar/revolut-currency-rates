package com.revolut.di.module

import com.revolut.api.ApiConfig
import com.revolut.api.CurrencyRatesService
import dagger.Module
import dagger.Provides
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class ApiModule {

    @Provides
    fun provideRetrofit(apiConfig: ApiConfig, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiConfig.baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    @Provides
    fun provideOkHttpClient(apiConfig: ApiConfig): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val certificatePinner = CertificatePinner.Builder()
            .apply {
                for ((host, pins) in apiConfig.certificatePins)
                    for (pin in pins) {
                        add(host, pin)
                    }
            }
            .build()

        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .apply {
                if (apiConfig.sslPinning) {
                    certificatePinner(certificatePinner)
                } else {
                    addInterceptor(logging)
                }
            }
            .build()
    }

    @Provides
    fun provideApiConfig(): ApiConfig {
        val map = mapOf(
            "hiring.revolut.codes" to listOf(
                "sha256/Te5cY1Lw/OyUKwfy5krouhTenDyX6u4l9XUQxRmXi0A=",
                "sha256/4a6cPehI7OG6cuDZka5NDZ7FR8a60d3auda+sKfg4Ng=",
                "sha256/x4QzPSC810K5/cMjb05Qm4k3Bw5zBn4lTdO/nEW/Td4="
            )
        )
        return ApiConfig.Builder()
            .baseUrl("https://hiring.revolut.codes/")
            .sslPinning(false)
            .certificatePins(map)
            .build()
    }

    @Provides
    fun provideCurrencyRatesService(retrofit: Retrofit): CurrencyRatesService =
        retrofit.create(CurrencyRatesService::class.java)
}