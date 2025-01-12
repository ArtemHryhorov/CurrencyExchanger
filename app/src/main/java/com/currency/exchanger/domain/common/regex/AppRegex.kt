package com.currency.exchanger.domain.common.regex

object AppRegex {
    val validAmountWithTwoDigits: Regex = Regex("^\\d+(\\.\\d{0,2})?$")
}