package com.currency.exchanger

import com.currency.exchanger.data.preferences.PreferencesManager
import com.currency.exchanger.domain.model.Currency
import com.currency.exchanger.domain.usecase.CalculateConversionUseCase
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.math.RoundingMode

class CalculateConversionUseCaseTest {

    private lateinit var preferencesManager: PreferencesManager
    private lateinit var calculateConversionUseCase: CalculateConversionUseCase

    @Before
    fun setup() {
        preferencesManager = mockk()
        calculateConversionUseCase = CalculateConversionUseCase(preferencesManager)
    }

    @Test
    fun `invoke should convert amount correctly without fee`() {
        // Given
        val amount = 100.0
        val fromCurrency = Currency("USD", 1.0, "USD")
        val toCurrency = Currency("EUR", 0.9, "USD")
        every { preferencesManager.getCompletedTransactions() } returns 3

        // When
        val result = calculateConversionUseCase(amount, fromCurrency, toCurrency)

        // Then
        val expectedConvertedAmount = (toCurrency.rateToBase / fromCurrency.rateToBase) * amount
        assertEquals(expectedConvertedAmount.roundTo2DecimalPlaces(), result.toReceive.roundTo2DecimalPlaces(), 0.001)
        assertEquals(0.0, result.fee, 0.001)
    }

    @Test
    fun `invoke should convert amount correctly with fee`() {
        // Given
        val amount = 100.0
        val fromCurrency = Currency("USD", 1.0, "USD")
        val toCurrency = Currency("EUR", 0.9, "USD")
        every { preferencesManager.getCompletedTransactions() } returns 6

        // When
        val result = calculateConversionUseCase(amount, fromCurrency, toCurrency)

        // Then
        val expectedConvertedAmount = (toCurrency.rateToBase / fromCurrency.rateToBase) * amount
        val expectedFee = amount * (AppConstants.TRANSACTION_FEE_PERCENT / 100)
        assertEquals((expectedConvertedAmount - expectedFee).roundTo2DecimalPlaces(), result.toReceive.roundTo2DecimalPlaces(), 0.001)
        assertEquals(expectedFee.roundTo2DecimalPlaces(), result.fee.roundTo2DecimalPlaces(), 0.001)
    }

    @Test
    fun `invoke should handle zero amount correctly`() {
        // Given
        val amount = 0.0
        val fromCurrency = Currency("USD", 1.0, "USD")
        val toCurrency = Currency("EUR", 0.9, "USD")
        every { preferencesManager.getCompletedTransactions() } returns 3

        // When
        val result = calculateConversionUseCase(amount, fromCurrency, toCurrency)

        // Then
        assertEquals(0.0, result.toReceive, 0.001)
        assertEquals(0.0, result.fee, 0.001)
    }

    @Test
    fun `invoke should handle different exchange rates correctly`() {
        // Given
        val amount = 100.0
        val fromCurrency = Currency("USD", 1.2, "USD")
        val toCurrency = Currency("EUR", 0.9, "USD")
        every { preferencesManager.getCompletedTransactions() } returns 3

        // When
        val result = calculateConversionUseCase(amount, fromCurrency, toCurrency)

        // Then
        val expectedConvertedAmount = (toCurrency.rateToBase / fromCurrency.rateToBase) * amount
        assertEquals(expectedConvertedAmount.roundTo2DecimalPlaces(), result.toReceive.roundTo2DecimalPlaces(), 0.001)
        assertEquals(0.0, result.fee, 0.001)
    }

    @Test
    fun `invoke should handle same currencies correctly`() {
        // Given
        val amount = 100.0
        val fromCurrency = Currency("USD", 1.0, "USD")
        val toCurrency = Currency("USD", 1.0, "USD")
        every { preferencesManager.getCompletedTransactions() } returns 3

        // When
        val result = calculateConversionUseCase(amount, fromCurrency, toCurrency)

        // Then
        assertEquals(amount, result.toReceive, 0.001)
        assertEquals(0.0, result.fee, 0.001)
    }

    private fun Double.roundTo2DecimalPlaces(): Double {
        return BigDecimal(this).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }
}