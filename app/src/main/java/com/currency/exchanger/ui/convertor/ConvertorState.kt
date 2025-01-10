package com.currency.exchanger.ui.convertor

import com.currency.exchanger.domain.model.Currency
import com.currency.exchanger.domain.model.UserBalance

data class ConvertorState(
    val userBalance: UserBalance = UserBalance(currencyBalanceList = emptyList()),
    val allCurrencies: List<Currency> = emptyList(),
    val currencyForSale: Currency? = null,
    val currencyForSaleAmount: String? = null,
    val currencyToReceive: Currency? = null,
    val currencyToReceiveAmount: String? = null,
)