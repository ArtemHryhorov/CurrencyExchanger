package com.currency.exchanger.ui.common

import androidx.annotation.StringRes
import com.currency.exchanger.R

enum class ValidationError(val uiError: UiError) {
    INSUFFICIENT_BALANCE(uiError = UiError(R.string.insufficient_balance_error)),
    INCORRECT_AMOUNT(uiError = UiError(R.string.incorrect_amount_error))
}

data class UiError(
    @StringRes val errorMessageRes: Int
)