package com.revolut.api

class ApiConfig private constructor(
    internal val baseUrl: String,
    internal val sslPinning: Boolean,
    internal val certificatePins: Map<String, List<String>>
) {

    class Builder {
        private lateinit var baseUrl: String
        private var sslPinning: Boolean = false
        private var certificatePins: Map<String, List<String>> = emptyMap()
        fun baseUrl(baseUrl: String) = apply { this.baseUrl = baseUrl }
        fun sslPinning(sslPinning: Boolean) = apply { this.sslPinning = sslPinning }
        fun certificatePins(certificatePins: Map<String, List<String>>) =
            apply { this.certificatePins = certificatePins }

        fun build() = ApiConfig(baseUrl, sslPinning, certificatePins)
    }
}