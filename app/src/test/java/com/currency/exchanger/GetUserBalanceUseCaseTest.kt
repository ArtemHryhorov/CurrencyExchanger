package com.currency.exchanger

import com.currency.exchanger.data.db.dao.UserBalanceDao
import com.currency.exchanger.domain.usecase.GetUserBalanceUseCase
import com.google.gson.Gson
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetUserBalanceUseCaseTest {

    private lateinit var userBalanceDao: UserBalanceDao
    private lateinit var getUserBalanceUseCase: GetUserBalanceUseCase
    private lateinit var gson: Gson

    @Before
    fun setup() {
        userBalanceDao = mockk()
        getUserBalanceUseCase = GetUserBalanceUseCase(userBalanceDao)
        gson = Gson()
    }

    @Test
    fun `invoke should create and return new user balance when not available`() = runTest {
        // Given
        coEvery { userBalanceDao.getUserBalance() } returns null
        coEvery { userBalanceDao.insertUserBalance(any()) } returns Unit

        // When
        val result = getUserBalanceUseCase()

        // Then
        assertTrue(result.isSuccess)
        val actualBalance = result.getOrNull()
        assertTrue(actualBalance != null)
        assertEquals(1, actualBalance?.currencyBalanceList?.size)
        assertEquals("EUR", actualBalance?.currencyBalanceList?.first()?.currency?.name)
    }

    @Test
    fun `invoke should return failure when exception occurs`() = runTest {
        // Given
        val exception = RuntimeException("Database error")
        coEvery { userBalanceDao.getUserBalance() } throws exception

        // When
        val result = getUserBalanceUseCase()

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}