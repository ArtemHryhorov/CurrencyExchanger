package com.currency.exchanger

import android.util.Log
import com.currency.exchanger.data.db.dao.UserBalanceDao
import com.currency.exchanger.data.preferences.PreferencesManager
import com.currency.exchanger.domain.model.Currency
import com.currency.exchanger.domain.model.CurrencyBalance
import com.currency.exchanger.domain.model.UserBalance
import com.currency.exchanger.domain.usecase.PerformTransactionUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PerformTransactionUseCaseTest {

    private lateinit var userBalanceDao: UserBalanceDao
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var performTransactionUseCase: PerformTransactionUseCase

    @Before
    fun setup() {
        mockkStatic(Log::class)
        coEvery { Log.d(any(), any()) } returns 0
        userBalanceDao = mockk()
        preferencesManager = mockk()
        performTransactionUseCase = PerformTransactionUseCase(userBalanceDao, preferencesManager)
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
    }

    @Test
    fun `invoke should update user balance correctly when selling and receiving existing currencies`() = runTest {
        // Given
        val sellCurrency = Currency("USD", 1.0, "USD")
        val receiveCurrency = Currency("EUR", 0.9, "USD")
        val initialUserBalance = UserBalance(
            currencyBalanceList = listOf(
                CurrencyBalance(100.0, sellCurrency),
                CurrencyBalance(50.0, receiveCurrency)
            )
        )
        val sellAmount = 20.0
        val receiveAmount = 18.0
        val fee = 1.0
        coEvery { userBalanceDao.insertUserBalance(any()) } returns Unit
        coEvery { preferencesManager.incrementCompletedTransactions() } returns Unit

        // When
        val result = performTransactionUseCase(
            userBalance = initialUserBalance,
            sellCurrency = sellCurrency,
            sellAmount = sellAmount,
            receiveCurrency = receiveCurrency,
            receiveAmount = receiveAmount,
            fee = fee,
        )

        // Then
        val expectedUserBalance = UserBalance(
            currencyBalanceList = listOf(
                CurrencyBalance(80.0, sellCurrency),
                CurrencyBalance(68.0, receiveCurrency)
            )
        )
        assertEquals(Result.success(expectedUserBalance), result)
        coVerify(exactly = 1) { userBalanceDao.insertUserBalance(any()) }
        coVerify(exactly = 1) { preferencesManager.incrementCompletedTransactions() }
    }

    @Test
    fun `invoke should update user balance correctly when receiving a new currency`() = runTest {
        // Given
        val sellCurrency = Currency("USD", 1.0, "USD")
        val receiveCurrency = Currency("GBP", 0.8, "USD")
        val initialUserBalance = UserBalance(
            currencyBalanceList = listOf(
                CurrencyBalance(100.0, sellCurrency),
            )
        )
        val sellAmount = 20.0
        val receiveAmount = 16.0
        val fee = 1.0
        coEvery { userBalanceDao.insertUserBalance(any()) } returns Unit
        coEvery { preferencesManager.incrementCompletedTransactions() } returns Unit

        // When
        val result = performTransactionUseCase(
            userBalance = initialUserBalance,
            sellCurrency = sellCurrency,
            sellAmount = sellAmount,
            receiveCurrency = receiveCurrency,
            receiveAmount = receiveAmount,
            fee = fee,
        )

        // Then
        val expectedUserBalance = UserBalance(
            currencyBalanceList = listOf(
                CurrencyBalance(80.0, sellCurrency),
                CurrencyBalance(16.0, receiveCurrency)
            )
        )
        assertEquals(Result.success(expectedUserBalance), result)
        coVerify(exactly = 1) { userBalanceDao.insertUserBalance(any()) }
        coVerify(exactly = 1) { preferencesManager.incrementCompletedTransactions() }
    }

    @Test
    fun `invoke should return failure when sell currency is not found`() = runTest {
        // Given
        val sellCurrency = Currency("USD", 1.0, "USD")
        val receiveCurrency = Currency("EUR", 0.9, "USD")
        val initialUserBalance = UserBalance(
            currencyBalanceList = listOf(
                CurrencyBalance(50.0, receiveCurrency)
            )
        )
        val sellAmount = 20.0
        val receiveAmount = 18.0
        val fee = 1.0

        // When
        val result = performTransactionUseCase(
            userBalance = initialUserBalance,
            sellCurrency = sellCurrency,
            sellAmount = sellAmount,
            receiveCurrency = receiveCurrency,
            receiveAmount = receiveAmount,
            fee = fee,
        )

        // Then
        assertEquals(true, result.isFailure)
        coVerify(exactly = 0) { userBalanceDao.insertUserBalance(any()) }
        coVerify(exactly = 0) { preferencesManager.incrementCompletedTransactions() }
    }
}