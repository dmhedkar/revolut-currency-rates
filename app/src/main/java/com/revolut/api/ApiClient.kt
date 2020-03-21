package com.revolut.api

import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {

    companion object {
        private const val TIMEOUT = 10L
        private lateinit var retrofit: Retrofit
        private lateinit var httpClient: OkHttpClient
        private lateinit var apiConfig: ApiConfig

        fun init(apiConfig: ApiConfig) = apply {
            this.apiConfig = apiConfig
            if (!::httpClient.isInitialized) {
                httpClient = getOkHttpClient();
            }

            if (!::retrofit.isInitialized) {
                retrofit = getRetrofit()
            }
        }

        fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(apiConfig.baseUrl)
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }

        internal fun getOkHttpClient(): OkHttpClient {

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
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .apply {
                    if (apiConfig.sslPinning) {
                        certificatePinner(certificatePinner)
                    } else {
                        addInterceptor(logging)
                    }
                }
                .build()
        }
    }

}