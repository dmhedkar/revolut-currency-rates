package com.revolut.api

fun getApiConfig(): ApiConfig {
    val map = mapOf(
        "hiring.revolut.codes" to listOf(
            "sha256/Te5cY1Lw/OyUKwfy5krouhTenDyX6u4l9XUQxRmXi0A=",
            "sha256/4a6cPehI7OG6cuDZka5NDZ7FR8a60d3auda+sKfg4Ng=",
            "sha256/x4QzPSC810K5/cMjb05Qm4k3Bw5zBn4lTdO/nEW/Td4="
        )
    )
    return ApiConfig.Builder()
        .baseUrl("https://hiring.revolut.codes/")
        .sslPinning(true)
        .certificatePins(map)
        .build()
}