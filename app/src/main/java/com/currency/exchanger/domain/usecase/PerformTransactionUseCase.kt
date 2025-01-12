package com.currency.exchanger.domain.usecase

import android.util.Log
import com.currency.exchanger.data.db.dao.UserBalanceDao
import com.currency.exchanger.data.db.mapper.toEntity
import com.currency.exchanger.data.preferences.PreferencesManager
import com.currency.exchanger.domain.model.Currency
import com.currency.exchanger.domain.model.CurrencyBalance
import com.currency.exchanger.domain.model.UserBalance
import javax.inject.Inject

class PerformTransactionUseCase @Inject constructor(
    private val userBalanceDao: UserBalanceDao,
    private val preferencesManager: PreferencesManager,
) {

    /**
     * This should be Back-End call, but we just changing user balance in local DB.
     */
    suspend operator fun invoke(
        userBalance: UserBalance,
        sellCurrency: Currency,
        sellAmount: Double,
        receiveCurrency: Currency,
        receiveAmount: Double,
        fee: Double,
    ): Result<UserBalance> = try {
        val updatedSellCurrencyBalance = transferCurrencyFromWallet(
            userBalance = userBalance,
            sellCurrency = sellCurrency,
            sellAmount = sellAmount,
        )
        val updatedReceiveCurrencyBalance = transferCurrencyToWallet(
            userBalance = userBalance,
            receiveCurrency = receiveCurrency,
            receiveAmount = receiveAmount,
        )

        val updatedBalance = updateUserBalance(
            userBalance = userBalance,
            updatedSellCurrencyBalance = updatedSellCurrencyBalance,
            updatedReceiveCurrencyBalance = updatedReceiveCurrencyBalance,
            fee = fee,
        )
        Result.success(updatedBalance)
    } catch (error: Throwable) {
        Result.failure(error)
    }

    private fun transferCurrencyFromWallet(
        userBalance: UserBalance,
        sellCurrency: Currency,
        sellAmount: Double,
    ): CurrencyBalance {
        val sellCurrencyBalance = userBalance
            .currencyBalanceList
            .find { it.currency == sellCurrency }
            ?: throw Exception("No currency found")
        Log.d("Convertor", "- $sellAmount ${sellCurrency.name}")
        return sellCurrencyBalance.copy(amount = sellCurrencyBalance.amount - sellAmount)
    }

    private fun transferCurrencyToWallet(
        userBalance: UserBalance,
        receiveCurrency: Currency,
        receiveAmount: Double,
    ): CurrencyBalance {
        val receiveCurrencyBalance = userBalance
            .currencyBalanceList
            .find { it.currency == receiveCurrency }
        Log.d("Convertor", "+ $receiveAmount ${receiveCurrency.name}")
        return CurrencyBalance(
            currency = receiveCurrency,
            amount = (receiveCurrencyBalance?.amount ?: 0.0) + receiveAmount,
        )
    }

    private suspend fun updateUserBalance(
        userBalance: UserBalance,
        updatedSellCurrencyBalance: CurrencyBalance,
        updatedReceiveCurrencyBalance: CurrencyBalance,
        fee: Double,
    ): UserBalance {
        // Update user balance
        val updatedBalanceList = userBalance
            .currencyBalanceList
            .toMutableList()
        updatedBalanceList.replaceAll {
            when {
                (it.currency == updatedSellCurrencyBalance.currency) -> updatedSellCurrencyBalance
                (it.currency == updatedReceiveCurrencyBalance.currency) -> updatedReceiveCurrencyBalance
                else -> it
            }
        }

        // If receiveCurrency is new to wallet - add this currency to wallet
        if (updatedBalanceList.any { it.currency == updatedReceiveCurrencyBalance.currency }.not()) {
            updatedBalanceList.add(updatedReceiveCurrencyBalance)
        }

        // Transfer fee somewhere if needed
        Log.d("Convertor", "$fee ${updatedSellCurrencyBalance.currency.name} fee applied")
        userBalanceDao.insertUserBalance(UserBalance(updatedBalanceList).toEntity())
        preferencesManager.incrementCompletedTransactions()
        return UserBalance(updatedBalanceList)
    }
}