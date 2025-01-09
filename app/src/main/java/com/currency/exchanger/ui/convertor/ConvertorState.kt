package com.currency.exchanger.ui.convertor

import com.currency.exchanger.domain.UserBalance

data class ConvertorState(
    val userBalance: UserBalance = UserBalance(currencyBalanceList = emptyList()),
)