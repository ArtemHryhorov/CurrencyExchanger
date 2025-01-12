package com.currency.exchanger.domain.usecase

import com.currency.exchanger.domain.exception.NoInternetConnectionException
import com.currency.exchanger.domain.model.Currency
import com.currency.exchanger.domain.repository.CurrencyRepository
import java.net.UnknownHostException
import javax.inject.Inject

class GetAllCurrencyUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository,
) {
    suspend operator fun invoke(): Result<List<Currency>> = try {
        Result.success(currencyRepository.getAllCurrency())
    } catch (error: UnknownHostException) {
        Result.failure(NoInternetConnectionException(error))
    } catch (error: Throwable) {
        Result.failure(error)
    }
}