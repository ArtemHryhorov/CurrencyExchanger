package com.currency.exchanger.ui.features.convertor

import com.currency.exchanger.domain.model.Currency
import com.currency.exchanger.domain.model.UserBalance
import com.currency.exchanger.ui.common.ValidationError
import com.currency.exchanger.ui.features.convertor.model.ConversionCompleted

data class ConvertorState(
    val userBalance: UserBalance = UserBalance(currencyBalanceList = emptyList()),
    val allCurrencies: List<Currency> = emptyList(),
    val currencyForSale: Currency? = null,
    val currencyForSaleAmount: String? = null,
    val currencyForSaleError: ValidationError? = null,
    val currencyToReceive: Currency? = null,
    val currencyToReceiveAmount: Double? = null,
    val fee: Double = 0.0,
    val conversionCompleted: ConversionCompleted? = null,
    val isLoading: Boolean = true,
) {

    /**
     * The state could be treated as valid when following conditions are met:
     * 1. No validation error.
     * 2. Both sell amd receive amounts calculated.
     * 3. Sell and receive currencies are different.
     */
    val isValidToSubmit: Boolean
        get() {
            return currencyForSaleError == null &&
                    currencyForSaleAmount != null &&
                    currencyToReceiveAmount != null &&
                    currencyForSale != currencyToReceive

        }
}