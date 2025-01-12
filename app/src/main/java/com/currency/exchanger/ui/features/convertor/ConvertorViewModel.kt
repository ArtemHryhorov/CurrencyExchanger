package com.currency.exchanger.ui.features.convertor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.exchanger.domain.common.extensions.isValidAmount
import com.currency.exchanger.domain.model.Currency
import com.currency.exchanger.domain.usecase.CalculateConversionUseCase
import com.currency.exchanger.domain.usecase.GetAllCurrencyUseCase
import com.currency.exchanger.domain.usecase.GetUserBalanceUseCase
import com.currency.exchanger.domain.usecase.PerformTransactionUseCase
import com.currency.exchanger.ui.common.ValidationError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConvertorViewModel @Inject constructor(
    private val getAllCurrencyUseCase: GetAllCurrencyUseCase,
    private val getUserBalanceUseCase: GetUserBalanceUseCase,
    private val calculateConversionUseCase: CalculateConversionUseCase,
    private val performTransactionUseCase: PerformTransactionUseCase,
) : ViewModel() {

    private val _convertorState = MutableStateFlow(ConvertorState())
    val convertorState = _convertorState.asStateFlow()

    fun onEvent(event: ConvertorEvent) {
        when (event) {
            ConvertorEvent.InitialDataLoading -> initialDataLoading()
            ConvertorEvent.Submit -> performCurrenciesConversion()
            is ConvertorEvent.OnReceiveCurrencySelected -> onReceiveCurrencySelected(event.currency)
            is ConvertorEvent.OnSellCurrencySelected -> onSellCurrencySelected(event.currency)
            is ConvertorEvent.OnSellAmountChanged -> onSellAmountChanged(event.amount)
        }
    }

    private fun initialDataLoading() {
        viewModelScope.launch(Dispatchers.IO) {
            val allCurrencies = getAllCurrencyUseCase()
            _convertorState.update { convertorState ->
                convertorState.copy(
                    userBalance = getUserBalanceUseCase(),
                    currencyForSale = allCurrencies.first(),
                    currencyToReceive = allCurrencies[1],
                    allCurrencies = allCurrencies,
                )
            }
        }
    }

    private fun performCurrenciesConversion() {
        viewModelScope.launch(Dispatchers.IO) {
            _convertorState.update { state ->
                val updatedUserBalance = with(state) {
                    performTransactionUseCase(
                        userBalance = userBalance,
                        sellCurrency = currencyForSale ?: return@launch,
                        sellAmount = currencyForSaleAmount?.toDoubleOrNull() ?: return@launch,
                        receiveCurrency = currencyToReceive ?: return@launch,
                        receiveAmount = currencyToReceiveAmount ?: return@launch,
                        fee = fee,
                    )
                }
                state.copy(
                    userBalance = updatedUserBalance,
                    currencyForSaleAmount = null,
                    currencyToReceiveAmount = null,
                )
            }
        }
    }

    private fun onReceiveCurrencySelected(currency: Currency) {
        _convertorState.update { state ->
            val isReceiveAmountEmpty =
                state.currencyToReceiveAmount != null && state.currencyToReceiveAmount > 0.0
            state.copy(
                currencyToReceive = currency,
                currencyToReceiveAmount = if (isReceiveAmountEmpty) {
                    calculateConversionUseCase(
                        amount = state.currencyForSaleAmount?.toDoubleOrNull() ?: 0.0,
                        fromCurrency = state.currencyForSale ?: return,
                        toCurrency = currency,
                    )
                } else state.currencyToReceiveAmount,
            )
        }
    }

    private fun onSellCurrencySelected(currency: Currency) {
        _convertorState.update { state ->
            if (state.currencyForSaleError == ValidationError.INCORRECT_AMOUNT) {
                state.copy(currencyForSale = currency)
            } else {
                val currencyBalance = state
                    .userBalance
                    .currencyBalanceList
                    .find { it.currency == currency }
                val saleAmount = state.currencyForSaleAmount?.toDoubleOrNull() ?: 0.0
                val isEnoughMoney = currencyBalance?.amount?.let { it >= saleAmount } ?: false
                if (isEnoughMoney.not() && saleAmount != 0.0) {
                    state.copy(
                        currencyForSale = currency,
                        currencyForSaleError = ValidationError.INSUFFICIENT_BALANCE
                    )
                } else {
                    state.copy(
                        currencyForSale = currency,
                        currencyToReceiveAmount = if (saleAmount != 0.0) {
                            calculateConversionUseCase(
                                amount = state.currencyForSaleAmount?.toDoubleOrNull() ?: 0.0,
                                fromCurrency = currency,
                                toCurrency = state.currencyToReceive ?: return,
                            )
                        } else state.currencyToReceiveAmount,
                    )
                }
            }
        }
    }

    private fun onSellAmountChanged(amount: String) {
        val isNewAmountValid = amount.isValidAmount
        _convertorState.update { state ->
            if (isNewAmountValid.not()) {
                state.copy(
                    currencyForSaleAmount = amount,
                    currencyForSaleError = ValidationError.INCORRECT_AMOUNT,
                    currencyToReceiveAmount = null,
                )
            } else {
                val currencyBalance = state
                    .userBalance
                    .currencyBalanceList
                    .find { it.currency == state.currencyForSale }
                val saleAmount = amount.toDoubleOrNull() ?: 0.0
                val isEnoughMoney = currencyBalance?.amount?.let { it >= saleAmount } ?: false
                if (isEnoughMoney.not()) {
                    state.copy(
                        currencyForSaleAmount = amount,
                        currencyForSaleError = ValidationError.INSUFFICIENT_BALANCE,
                        currencyToReceiveAmount = null,
                    )
                } else {
                    state.copy(
                        currencyForSaleAmount = amount,
                        currencyForSaleError = null,
                        currencyToReceiveAmount = calculateConversionUseCase(
                            amount = amount.toDouble(),
                            fromCurrency = state.currencyForSale ?: return,
                            toCurrency = state.currencyToReceive ?: return,
                        ),
                    )
                }
            }
        }
    }
}