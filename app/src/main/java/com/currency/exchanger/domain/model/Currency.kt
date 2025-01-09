package com.currency.exchanger.domain.model

data class Currency(
    val name: String,
    val rateToBase: Double,
    val baseCurrencyName: String,
)
