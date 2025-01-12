package com.currency.exchanger.ui.features.convertor

import com.currency.exchanger.domain.model.Currency

sealed interface ConvertorEvent {
    data object InitialDataLoading : ConvertorEvent
    data object Submit : ConvertorEvent
    data class OnReceiveCurrencySelected(val currency: Currency) : ConvertorEvent
    data class OnSellCurrencySelected(val currency: Currency) : ConvertorEvent
    data class OnSellAmountChanged(val amount: String) : ConvertorEvent
}