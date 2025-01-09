package com.currency.exchanger.domain.extensions

import java.util.Locale

fun Double.formatAmountWithDecimals(decimalPlaces: Int): String {
    return String.format(Locale.ROOT, "%.${decimalPlaces}f", this)
}