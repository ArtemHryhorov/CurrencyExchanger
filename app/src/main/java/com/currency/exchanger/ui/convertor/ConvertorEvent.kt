package com.currency.exchanger.ui.convertor

import com.currency.exchanger.domain.model.Currency

sealed interface ConvertorEvent {
    data object LoadUserBalance : ConvertorEvent
    data object Submit : ConvertorEvent
    data class OnSellCurrencySelected(val currency: Currency) : ConvertorEvent
    data class OnSellAmountChanged(val amount: String) : ConvertorEvent
    data class OnReceiveCurrencySelected(val currency: Currency) : ConvertorEvent
    data class OnReceiveAmountChanged(val amount: String) : ConvertorEvent
}