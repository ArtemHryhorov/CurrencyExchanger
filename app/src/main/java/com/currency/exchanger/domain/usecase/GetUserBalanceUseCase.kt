package com.currency.exchanger.domain.usecase

import com.currency.exchanger.domain.model.UserBalance
import javax.inject.Inject

class GetUserBalanceUseCase @Inject constructor() {
    operator fun invoke(): UserBalance = UserBalance.createNew()
}