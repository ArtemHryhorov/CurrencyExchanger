package com.currency.exchanger.ui.convertor

sealed interface ConvertorEvent {
    data object LoadUserBalance : ConvertorEvent
}