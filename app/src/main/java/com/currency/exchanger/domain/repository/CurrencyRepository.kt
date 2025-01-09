package com.currency.exchanger.domain.repository

import com.currency.exchanger.domain.model.Currency

fun interface CurrencyRepository {

    suspend fun getAllCurrency(): List<Currency>
}