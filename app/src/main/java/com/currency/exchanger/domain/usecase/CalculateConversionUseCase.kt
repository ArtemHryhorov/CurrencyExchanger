package com.currency.exchanger.domain.usecase

import com.currency.exchanger.domain.model.Currency
import javax.inject.Inject

class CalculateConversionUseCase @Inject constructor() {

    /**
     * Converts an amount from one currency to another using their exchange rates.
     *
     *
     * @param amount The amount in the `fromCurrency` to be converted.
     * @param fromCurrency The currency from which the conversion starts.
     * @param toCurrency The currency to which the conversion is made.
     * @return The converted amount in the `toCurrency`.
     *
     */
    operator fun invoke(
        amount: Double,
        fromCurrency: Currency,
        toCurrency: Currency,
    ): Double = (toCurrency.rateToBase / fromCurrency.rateToBase) * amount
}