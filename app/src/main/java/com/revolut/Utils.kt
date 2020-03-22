package com.revolut

import com.revolut.api.CurrencyRatesResponse
import com.revolut.dto.CurrencyModel

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

fun mapCurrencyList(respo: CurrencyRatesResponse): List<CurrencyModel> {
    val list = mutableListOf<CurrencyModel>()
    list.add(CurrencyModel(Currency.EUR.name, respo.rates.EUR, "Euro"))
    list.add(CurrencyModel(Currency.AUD.name, respo.rates.AUD, "Australian Dollar"))
    list.add(CurrencyModel(Currency.BGN.name, respo.rates.BGN, "Bulgarian Lev"))
    list.add(CurrencyModel(Currency.BRL.name, respo.rates.BRL, "Brazilian Real"))
    list.add(CurrencyModel(Currency.CAD.name, respo.rates.CAD, "Canadian Dollar"))
    list.add(CurrencyModel(Currency.CHF.name, respo.rates.CHF, "Swiss Franc"))
    list.add(CurrencyModel(Currency.CNY.name, respo.rates.CNY, "Yuan Renminbi"))
    list.add(CurrencyModel(Currency.CZK.name, respo.rates.CZK, "Czech Koruna"))
    list.add(CurrencyModel(Currency.DKK.name, respo.rates.DKK, "Danish Krone"))
    list.add(CurrencyModel(Currency.GBP.name, respo.rates.GBP, "Pound Sterling"))
    list.add(CurrencyModel(Currency.HKD.name, respo.rates.HKD, "Hong Kong Dollar"))
    list.add(CurrencyModel(Currency.HRK.name, respo.rates.HRK, "Kuna"))
    list.add(CurrencyModel(Currency.HUF.name, respo.rates.HUF, "Forint"))
    list.add(CurrencyModel(Currency.IDR.name, respo.rates.IDR, "Rupiah"))
    list.add(CurrencyModel(Currency.ILS.name, respo.rates.ILS, "New Israeli Sheqel"))
    list.add(CurrencyModel(Currency.INR.name, respo.rates.INR, "Indian Rupee"))
    list.add(CurrencyModel(Currency.ISK.name, respo.rates.ISK, "Iceland Krona"))
    list.add(CurrencyModel(Currency.JPY.name, respo.rates.JPY, "Yen"))
    list.add(CurrencyModel(Currency.KRW.name, respo.rates.KRW, "Won"))
    list.add(CurrencyModel(Currency.MXN.name, respo.rates.MXN, "Mexican Peso"))
    list.add(CurrencyModel(Currency.MYR.name, respo.rates.MYR, "Malaysian Ringgit"))
    list.add(CurrencyModel(Currency.NOK.name, respo.rates.NOK, "Norwegian Krone"))
    list.add(CurrencyModel(Currency.NZD.name, respo.rates.NZD, "New Zealand Dollar"))
    list.add(CurrencyModel(Currency.PHP.name, respo.rates.PHP, "Philippine Peso"))
    list.add(CurrencyModel(Currency.PLN.name, respo.rates.PLN, "Zloty"))
    list.add(CurrencyModel(Currency.RON.name, respo.rates.RON, "Romanian Leu"))
    list.add(CurrencyModel(Currency.RUB.name, respo.rates.RUB, "Russian Ruble"))
    list.add(CurrencyModel(Currency.SEK.name, respo.rates.SEK, "Swedish Krona"))
    list.add(CurrencyModel(Currency.SGD.name, respo.rates.SGD, "Singapore Dollar"))
    list.add(CurrencyModel(Currency.THB.name, respo.rates.THB, "Baht"))
    list.add(CurrencyModel(Currency.USD.name, respo.rates.USD, "US Dollar"))
    list.find { it.currency == respo.baseCurrency }?.rate = 1.0
    return list
}


fun getCurrencyArray(
    currentList: List<CurrencyModel>,
    remoteList: List<CurrencyModel>
): Array<CurrencyModel> {
    if (currentList.isEmpty()) {
        return remoteList.toTypedArray()
    } else {
        for (item in currentList) {
            val find = remoteList.find { t -> t.currency == item.currency }
            find?.let { t -> item.rate = t.rate }
        }
    }
    return currentList.toTypedArray()
}