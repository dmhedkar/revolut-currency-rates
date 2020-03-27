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

private var currentList = mutableListOf<CurrencyModel>()

var defaultValue = 100

fun mapCurrencyList(respo: CurrencyRatesResponse): Array<CurrencyModel> {
    val list = mutableListOf<CurrencyModel>()
    list.add(CurrencyModel(Currency.EUR.name, defaultValue * respo.rates.EUR, "Euro"))
    list.add(CurrencyModel(Currency.AUD.name, defaultValue * respo.rates.AUD, "Australian Dollar"))
    list.add(CurrencyModel(Currency.BGN.name, defaultValue * respo.rates.BGN, "Bulgarian Lev"))
    list.add(CurrencyModel(Currency.BRL.name, defaultValue * respo.rates.BRL, "Brazilian Real"))
    list.add(CurrencyModel(Currency.CAD.name, defaultValue * respo.rates.CAD, "Canadian Dollar"))
    list.add(CurrencyModel(Currency.CHF.name, defaultValue * respo.rates.CHF, "Swiss Franc"))
    list.add(CurrencyModel(Currency.CNY.name, defaultValue * respo.rates.CNY, "Yuan Renminbi"))
    list.add(CurrencyModel(Currency.CZK.name, defaultValue * respo.rates.CZK, "Czech Koruna"))
    list.add(CurrencyModel(Currency.DKK.name, defaultValue * respo.rates.DKK, "Danish Krone"))
    list.add(CurrencyModel(Currency.GBP.name, defaultValue * respo.rates.GBP, "Pound Sterling"))
    list.add(CurrencyModel(Currency.HKD.name, defaultValue * respo.rates.HKD, "Hong Kong Dollar"))
    list.add(CurrencyModel(Currency.HRK.name, defaultValue * respo.rates.HRK, "Kuna"))
    list.add(CurrencyModel(Currency.HUF.name, defaultValue * respo.rates.HUF, "Forint"))
    list.add(CurrencyModel(Currency.IDR.name, defaultValue * respo.rates.IDR, "Rupiah"))
    list.add(CurrencyModel(Currency.ILS.name, defaultValue * respo.rates.ILS, "New Israeli Sheqel"))
    list.add(CurrencyModel(Currency.INR.name, defaultValue * respo.rates.INR, "Indian Rupee"))
    list.add(CurrencyModel(Currency.ISK.name, defaultValue * respo.rates.ISK, "Iceland Krona"))
    list.add(CurrencyModel(Currency.JPY.name, defaultValue * respo.rates.JPY, "Yen"))
    list.add(CurrencyModel(Currency.KRW.name, defaultValue * respo.rates.KRW, "Won"))
    list.add(CurrencyModel(Currency.MXN.name, defaultValue * respo.rates.MXN, "Mexican Peso"))
    list.add(CurrencyModel(Currency.MYR.name, defaultValue * respo.rates.MYR, "Malaysian Ringgit"))
    list.add(CurrencyModel(Currency.NOK.name, defaultValue * respo.rates.NOK, "Norwegian Krone"))
    list.add(CurrencyModel(Currency.NZD.name, defaultValue * respo.rates.NZD, "New Zealand Dollar"))
    list.add(CurrencyModel(Currency.PHP.name, defaultValue * respo.rates.PHP, "Philippine Peso"))
    list.add(CurrencyModel(Currency.PLN.name, defaultValue * respo.rates.PLN, "Zloty"))
    list.add(CurrencyModel(Currency.RON.name, defaultValue * respo.rates.RON, "Romanian Leu"))
    list.add(CurrencyModel(Currency.RUB.name, defaultValue * respo.rates.RUB, "Russian Ruble"))
    list.add(CurrencyModel(Currency.SEK.name, defaultValue * respo.rates.SEK, "Swedish Krona"))
    list.add(CurrencyModel(Currency.SGD.name, defaultValue * respo.rates.SGD, "Singapore Dollar"))
    list.add(CurrencyModel(Currency.THB.name, defaultValue * respo.rates.THB, "Baht"))
    list.add(CurrencyModel(Currency.USD.name, defaultValue * respo.rates.USD, "US Dollar"))
    list.find { it.currency == respo.baseCurrency }?.rate = defaultValue.toDouble()
    updateCurrencyArray(list)
    return getCurrencyArray()
}


fun getItem(index: Int) = currentList[index]

fun getCurrencyArray() = currentList.toTypedArray()

fun moveToTop(index: Int) {
    val item = currentList.removeAt(index)
    defaultValue = item.rate.toInt()
    currentList.add(0, item)
}

fun clearList() {
    defaultValue = 100
    currentList.clear()
}

private fun updateCurrencyArray(remoteList: List<CurrencyModel>) {
    if (currentList.isEmpty()) currentList = remoteList.toMutableList()
    for (item in currentList) {
        val find = remoteList.find { t -> t.currency == item.currency }
        find?.let { t -> item.rate = t.rate }
    }
}