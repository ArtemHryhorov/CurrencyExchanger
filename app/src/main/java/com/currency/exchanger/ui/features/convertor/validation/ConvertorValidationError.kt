package com.currency.exchanger.ui.features.convertor.validation

import com.currency.exchanger.R
import com.currency.exchanger.ui.common.error.UiError

enum class ValidationError(val uiError: UiError) {
    INSUFFICIENT_BALANCE(uiError = UiError(R.string.insufficient_balance_error)),
    INCORRECT_AMOUNT(uiError = UiError(R.string.incorrect_amount_error))
}