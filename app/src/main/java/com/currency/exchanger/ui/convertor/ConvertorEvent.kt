package com.currency.exchanger.ui.convertor

import com.currency.exchanger.domain.model.Currency

sealed interface ConvertorEvent {
    data object LoadUserBalance : ConvertorEvent
    data class OnSellCurrencySelected(val currency: Currency) : ConvertorEvent
    data class OnReceiveCurrencySelected(val currency: Currency) : ConvertorEvent
}