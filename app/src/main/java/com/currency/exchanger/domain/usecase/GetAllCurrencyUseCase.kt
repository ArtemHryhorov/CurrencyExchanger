package com.currency.exchanger.domain.usecase

import com.currency.exchanger.domain.model.Currency
import com.currency.exchanger.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetAllCurrencyUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository,
) {
    suspend operator fun invoke(): List<Currency> = currencyRepository.getAllCurrency()
}