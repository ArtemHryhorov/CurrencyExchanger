package com.currency.exchanger.ui.features.convertor.model

import com.currency.exchanger.domain.model.CurrencyBalance

data class ConversionCompleted(
    val sellCurrencyBalance: CurrencyBalance,
    val receiveCurrencyBalance: CurrencyBalance,
    val fee: CurrencyBalance,
)
