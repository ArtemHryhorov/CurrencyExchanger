package com.currency.exchanger.domain.usecase

import com.currency.exchanger.AppConstants
import com.currency.exchanger.data.preferences.PreferencesManager
import com.currency.exchanger.domain.model.ConversionResult
import com.currency.exchanger.domain.model.Currency
import javax.inject.Inject

class CalculateConversionUseCase @Inject constructor(
    private val preferencesManager: PreferencesManager,
) {

    /**
     * Converts an amount from one currency to another using their exchange rates
     * and applies a fee if specified conditions are met.
     *
     * The conversion is performed as follows:
     * 1. Calculate the converted amount using the exchange rates:
     *    `(toCurrency.rateToBase / fromCurrency.rateToBase) * amount`.
     * 2. Optionally calculate a fee based on the `amount`.
     * 3. Subtract the calculated fee from the converted amount.
     *
     * @param amount The amount in the `fromCurrency` to be converted.
     * @param fromCurrency The currency from which the conversion starts.
     * @param toCurrency The currency to which the conversion is made.
     * @return A `ConversionResult` containing:
     *         - `toReceive`: The converted amount in the `toCurrency` after deducting the fee.
     *         - `fee`: The fee applied to the conversion, if any.
     */
    operator fun invoke(
        amount: Double,
        fromCurrency: Currency,
        toCurrency: Currency,
    ): ConversionResult {
        val convertedAmount = (toCurrency.rateToBase / fromCurrency.rateToBase) * amount
        val shouldFeeBeApplied = preferencesManager.getCompletedTransactions() > 5
        val fee =
            if (shouldFeeBeApplied) amount * (AppConstants.TRANSACTION_FEE_PERCENT / 100) else 0.0
        return ConversionResult(
            toReceive = convertedAmount - fee,
            fee = fee
        )
    }
}