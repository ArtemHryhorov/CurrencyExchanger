package com.currency.exchanger.domain.common

object AppRegex {
    val validAmountWithTwoDigits: Regex = Regex("^\\d+(\\.\\d{0,2})?$")
}