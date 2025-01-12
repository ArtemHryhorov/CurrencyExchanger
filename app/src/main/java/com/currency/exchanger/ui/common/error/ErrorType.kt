package com.currency.exchanger.ui.common.error

import com.currency.exchanger.R

enum class ErrorType(val uiError: UiError) {
    NO_INTERNET_CONNECTION(uiError = UiError(R.string.no_internet_connection)),
    UNKNOWN(uiError = UiError(R.string.something_went_wrong)),
}