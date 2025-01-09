package com.currency.exchanger.domain

data class UserBalance(
    val currencyBalanceList: List<CurrencyBalance>,
) {

    companion object {
        fun createNew(): UserBalance = UserBalance(
            currencyBalanceList = listOf(CurrencyBalance(1000.0, Currency.EUR)),
        )
    }
}
