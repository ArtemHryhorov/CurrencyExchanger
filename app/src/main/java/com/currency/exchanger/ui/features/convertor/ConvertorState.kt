package com.currency.exchanger.ui.features.convertor

import com.currency.exchanger.domain.model.Currency
import com.currency.exchanger.domain.model.UserBalance
import com.currency.exchanger.ui.common.ValidationError

data class ConvertorState(
    val userBalance: UserBalance = UserBalance(currencyBalanceList = emptyList()),
    val allCurrencies: List<Currency> = emptyList(),
    val currencyForSale: Currency? = null,
    val currencyForSaleAmount: String? = null,
    val currencyForSaleError: ValidationError? = null,
    val currencyToReceive: Currency? = null,
    val currencyToReceiveAmount: String? = null,
    val currencyToReceiveError: ValidationError? = null,
) {

    val isValidToSubmit: Boolean
        get() = currencyForSaleError == null && currencyToReceiveError == null
}