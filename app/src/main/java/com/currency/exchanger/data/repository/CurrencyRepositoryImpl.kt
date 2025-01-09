package com.currency.exchanger.data.repository

import com.currency.exchanger.data.api.CurrencyApiService
import com.currency.exchanger.domain.model.Currency
import com.currency.exchanger.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyApiService: CurrencyApiService,
) : CurrencyRepository {

    override suspend fun getAllCurrency(): List<Currency> {
        val currencyListResponse = currencyApiService.getAllCurrency()
        val currencyList = mutableListOf(
            Currency(
                name = currencyListResponse.base,
                rateToBase = 1.0,
                baseCurrencyName = currencyListResponse.base,
            )
        )
        currencyListResponse.rates.forEach { (name, rate) ->
            currencyList.add(
                index = currencyList.size,
                element = Currency(
                    name = name,
                    rateToBase = rate,
                    baseCurrencyName = currencyListResponse.base,
                )
            )
        }
        return currencyList.toList()
    }
}