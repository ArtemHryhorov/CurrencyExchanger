package com.currency.exchanger.domain.common.extensions

import java.util.Locale

fun Double.formatAmountWithDecimals(decimalPlaces: Int): String {
    return String.format(Locale.ROOT, "%.${decimalPlaces}f", this)
}

val String.isValidAmount: Boolean
    get() = this.toDoubleOrNull() != null && this.toDouble() > 0.0