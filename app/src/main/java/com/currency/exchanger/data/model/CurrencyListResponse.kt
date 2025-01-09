package com.currency.exchanger.data.model

data class CurrencyListResponse(
    val base: String,
    val date: String,
    val rates: Map<String, Double>,
)
