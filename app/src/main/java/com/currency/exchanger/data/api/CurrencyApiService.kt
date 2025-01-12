package com.currency.exchanger.data.api

import com.currency.exchanger.data.api.model.CurrencyListResponse
import retrofit2.http.GET

fun interface CurrencyApiService {

    @GET("currency-exchange-rates")
    suspend fun getAllCurrency(): CurrencyListResponse
}