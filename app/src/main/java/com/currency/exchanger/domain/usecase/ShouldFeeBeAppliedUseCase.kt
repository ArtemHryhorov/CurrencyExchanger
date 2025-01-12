package com.currency.exchanger.domain.usecase

import com.currency.exchanger.data.preferences.PreferencesManager
import javax.inject.Inject

class ShouldFeeBeAppliedUseCase @Inject constructor(
    private val preferencesManager: PreferencesManager,
) {

    operator fun invoke(): Boolean = preferencesManager.getCompletedTransactions() >= 5
}