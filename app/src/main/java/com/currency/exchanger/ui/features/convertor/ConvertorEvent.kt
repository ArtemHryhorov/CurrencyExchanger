package com.currency.exchanger.ui.features.convertor

import com.currency.exchanger.domain.model.Currency

sealed interface ConvertorEvent {
    data object LoadUserBalance : ConvertorEvent
    data object StartCurrenciesDataSync : ConvertorEvent
    data object Submit : ConvertorEvent
    data object ConversionCompleted : ConvertorEvent
    data class OnReceiveCurrencySelected(val currency: Currency) : ConvertorEvent
    data class OnSellCurrencySelected(val currency: Currency) : ConvertorEvent
    data class OnSellAmountChanged(val amount: String) : ConvertorEvent
}