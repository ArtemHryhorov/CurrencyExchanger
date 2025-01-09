package com.currency.exchanger.domain

import java.util.Locale

data class CurrencyBalance(
    val amount: Double,
    val currency: Currency,
) {

    fun convertedUiBalance(): String {
        val roundedAmount = String.format(Locale.ROOT, "%.2f", amount)
        return "$roundedAmount ${currency.name}"
    }
}
