package com.currency.exchanger.data.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext context: Context) {

    private companion object {
        const val PREFERENCES_NAME = "app_preferences"
        const val COMPLETED_TRANSACTIONS_KEY = "completed_transactions"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun getCompletedTransactions() = sharedPreferences.getInt(COMPLETED_TRANSACTIONS_KEY, 0)

    fun incrementCompletedTransactions() {
        sharedPreferences.edit()
            .putInt(COMPLETED_TRANSACTIONS_KEY, getCompletedTransactions() + 1)
            .apply()
    }
}