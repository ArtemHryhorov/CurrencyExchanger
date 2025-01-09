package com.currency.exchanger.domain.model

data class UserBalance(
    val currencyBalanceList: List<CurrencyBalance>,
) {

    companion object {
        fun createNew(): UserBalance = UserBalance(
            currencyBalanceList = listOf(
                CurrencyBalance(
                    1000.0,
                    Currency(
                        name = "EUR",
                        rateToBase = 1.0,
                        baseCurrencyName = "EUR",
                    )
                )
            ),
        )
    }
}
