package com.currency.exchanger.domain.model

import com.currency.exchanger.domain.extensions.formatAmountWithDecimals

data class CurrencyBalance(
    val amount: Double,
    val currency: Currency,
) {

    // For example "95.99 USD"
    fun convertedUiBalance(): String {
        return "${amount.formatAmountWithDecimals(2)} ${currency.name}"
    }
}
