package com.revolut

import com.revolut.api.CurrencyRatesResponse
import com.revolut.cache.CurrencyRateEntity

enum class Currency {
    EUR,
    AUD,
    BGN,
    BRL,
    CAD,
    CHF,
    CNY,
    CZK,
    DKK,
    GBP,
    HKD,
    HRK,
    HUF,
    IDR,
    ILS,
    INR,
    ISK,
    JPY,
    KRW,
    MXN,
    MYR,
    NOK,
    NZD,
    PHP,
    PLN,
    RON,
    RUB,
    SEK,
    SGD,
    THB,
    USD
}

fun currencyList(respo: CurrencyRatesResponse): MutableList<CurrencyRateEntity> {
    val list = mutableListOf<CurrencyRateEntity>()
    list.add(CurrencyRateEntity(Currency.EUR.name, respo.rates.EUR, "Euro"))
    list.add(CurrencyRateEntity(Currency.AUD.name, respo.rates.AUD, "Australian Dollar"))
    list.add(CurrencyRateEntity(Currency.BGN.name, respo.rates.BGN, "Bulgarian Lev"))
    list.add(CurrencyRateEntity(Currency.BRL.name, respo.rates.BRL, "Brazilian Real"))
    list.add(CurrencyRateEntity(Currency.CAD.name, respo.rates.CAD, "Canadian Dollar"))
    list.add(CurrencyRateEntity(Currency.CHF.name, respo.rates.CHF, "Swiss Franc"))
    list.add(CurrencyRateEntity(Currency.CNY.name, respo.rates.CNY, "Yuan Renminbi"))
    list.add(CurrencyRateEntity(Currency.CZK.name, respo.rates.CZK, "Czech Koruna"))
    list.add(CurrencyRateEntity(Currency.DKK.name, respo.rates.DKK, "Danish Krone"))
    list.add(CurrencyRateEntity(Currency.GBP.name, respo.rates.GBP, "Pound Sterling"))
    list.add(CurrencyRateEntity(Currency.HKD.name, respo.rates.HKD, "Hong Kong Dollar"))
    list.add(CurrencyRateEntity(Currency.HRK.name, respo.rates.HRK, "Kuna"))
    list.add(CurrencyRateEntity(Currency.HUF.name, respo.rates.HUF, "Forint"))
    list.add(CurrencyRateEntity(Currency.IDR.name, respo.rates.IDR, "Rupiah"))
    list.add(CurrencyRateEntity(Currency.ILS.name, respo.rates.ILS, "New Israeli Sheqel"))
    list.add(CurrencyRateEntity(Currency.INR.name, respo.rates.INR, "Indian Rupee"))
    list.add(CurrencyRateEntity(Currency.ISK.name, respo.rates.ISK, "Iceland Krona"))
    list.add(CurrencyRateEntity(Currency.JPY.name, respo.rates.JPY, "Yen"))
    list.add(CurrencyRateEntity(Currency.KRW.name, respo.rates.KRW, "Won"))
    list.add(CurrencyRateEntity(Currency.MXN.name, respo.rates.MXN, "Mexican Peso"))
    list.add(CurrencyRateEntity(Currency.MYR.name, respo.rates.MYR, "Malaysian Ringgit"))
    list.add(CurrencyRateEntity(Currency.NOK.name, respo.rates.NOK, "Norwegian Krone"))
    list.add(CurrencyRateEntity(Currency.NZD.name, respo.rates.NZD, "New Zealand Dollar"))
    list.add(CurrencyRateEntity(Currency.PHP.name, respo.rates.PHP, "Philippine Peso"))
    list.add(CurrencyRateEntity(Currency.PLN.name, respo.rates.PLN, "Zloty"))
    list.add(CurrencyRateEntity(Currency.RON.name, respo.rates.RON, "Romanian Leu"))
    list.add(CurrencyRateEntity(Currency.RUB.name, respo.rates.RUB, "Russian Ruble"))
    list.add(CurrencyRateEntity(Currency.SEK.name, respo.rates.SEK, "Swedish Krona"))
    list.add(CurrencyRateEntity(Currency.SGD.name, respo.rates.SGD, "Singapore Dollar"))
    list.add(CurrencyRateEntity(Currency.THB.name, respo.rates.THB, "Baht"))
    list.add(CurrencyRateEntity(Currency.USD.name, respo.rates.USD, "US Dollar"))
    list.find { it.currencyCode == respo.baseCurrency }?.currencyRate = 1.0
    return list
}
