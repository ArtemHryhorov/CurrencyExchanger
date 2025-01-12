package com.currency.exchanger.domain.usecase

import com.currency.exchanger.data.db.dao.UserBalanceDao
import com.currency.exchanger.data.db.mapper.toDomain
import com.currency.exchanger.data.db.mapper.toEntity
import com.currency.exchanger.domain.model.UserBalance
import javax.inject.Inject

class GetUserBalanceUseCase @Inject constructor(
    private val userBalanceDao: UserBalanceDao,
) {
    suspend operator fun invoke(): Result<UserBalance> = try {
        val userBalance = userBalanceDao.getUserBalance()?.toDomain() ?: run {
            // Create new user balance and save to DB if it's empty
            val newUserBalance = UserBalance.createNew()
            userBalanceDao.insertUserBalance(newUserBalance.toEntity())
            newUserBalance
        }
        Result.success(userBalance)
    } catch (error: Throwable) {
        Result.failure(error)
    }
}