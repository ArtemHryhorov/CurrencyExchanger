package com.currency.exchanger.ui.features.convertor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.exchanger.AppConstants
import com.currency.exchanger.domain.common.extensions.isValidAmount
import com.currency.exchanger.domain.model.Currency
import com.currency.exchanger.domain.model.CurrencyBalance
import com.currency.exchanger.domain.usecase.CalculateConversionUseCase
import com.currency.exchanger.domain.usecase.GetAllCurrencyUseCase
import com.currency.exchanger.domain.usecase.GetUserBalanceUseCase
import com.currency.exchanger.domain.usecase.PerformTransactionUseCase
import com.currency.exchanger.ui.common.ValidationError
import com.currency.exchanger.ui.features.convertor.model.ConversionCompleted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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

    private var syncCurrenciesJob: Job? = null
    private val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
        throwable.printStackTrace()
    }

    override fun onCleared() {
        syncCurrenciesJob?.cancel()
        super.onCleared()
    }

    fun onEvent(event: ConvertorEvent) {
        when (event) {
            ConvertorEvent.LoadUserBalance -> loadUserBalance()
            ConvertorEvent.StartCurrenciesDataSync -> startCurrenciesSyncJob()
            ConvertorEvent.Submit -> performCurrenciesConversion()
            ConvertorEvent.ConversionCompleted -> conversionCompleted()
            is ConvertorEvent.OnReceiveCurrencySelected -> onReceiveCurrencySelected(event.currency)
            is ConvertorEvent.OnSellCurrencySelected -> onSellCurrencySelected(event.currency)
            is ConvertorEvent.OnSellAmountChanged -> onSellAmountChanged(event.amount)
        }
    }

    private fun loadUserBalance() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            _convertorState.update { convertorState ->
                convertorState.copy(userBalance = getUserBalanceUseCase())
            }
        }
    }

    private fun startCurrenciesSyncJob() {
        if (syncCurrenciesJob?.isActive == true) return
        syncCurrenciesJob = viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            while (true) {
                val allCurrencies = getAllCurrencyUseCase()
                Log.d("Convertor", "Currencies synchronized. Amount: ${allCurrencies.size}")
                _convertorState.update { state ->
                    if (state.currencyForSale == null && state.currencyToReceive == null) {
                        setInitialCurrencies(allCurrencies)
                    }
                    state.copy(allCurrencies = allCurrencies)
                }
                delay(AppConstants.CURRENCIES_SYNC_DELAY)
            }
        }
    }

    /*
        We could remember last "sell" and "receive" currencies for better UX,
        but for simplification we are using first two currencies from the list.
    */
    private fun setInitialCurrencies(allCurrencies: List<Currency>) {
        _convertorState.update { state ->
            state.copy(
                currencyForSale = allCurrencies.first(),
                currencyToReceive = allCurrencies[1],
            )
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
                    conversionCompleted = ConversionCompleted(
                        sellCurrencyBalance = CurrencyBalance(
                            amount = state.currencyForSaleAmount?.toDoubleOrNull() ?: return@launch,
                            currency = state.currencyForSale ?: return@launch,
                        ),
                        receiveCurrencyBalance = CurrencyBalance(
                            amount = state.currencyToReceiveAmount ?: return@launch,
                            currency = state.currencyToReceive ?: return@launch,
                        ),
                        fee = CurrencyBalance(
                            amount = state.fee,
                            currency = state.currencyForSale,
                        ),
                    ),
                )
            }
        }
    }

    private fun conversionCompleted() {
        _convertorState.update { state ->
            state.copy(
                currencyForSaleAmount = null,
                currencyToReceiveAmount = null,
                conversionCompleted = null,
            )
        }
    }

    private fun onReceiveCurrencySelected(currency: Currency) {
        _convertorState.update { state ->
            val isReceiveAmountEmpty =
                state.currencyToReceiveAmount != null && state.currencyToReceiveAmount > 0.0
            val conversionResult = calculateConversionUseCase(
                amount = state.currencyForSaleAmount?.toDoubleOrNull() ?: 0.0,
                fromCurrency = state.currencyForSale ?: return,
                toCurrency = currency,
            )
            state.copy(
                currencyToReceive = currency,
                currencyToReceiveAmount = if (isReceiveAmountEmpty) {
                    conversionResult.toReceive
                } else state.currencyToReceiveAmount,
                fee = conversionResult.fee,
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
                    val conversionResult = calculateConversionUseCase(
                        amount = state.currencyForSaleAmount?.toDoubleOrNull() ?: 0.0,
                        fromCurrency = currency,
                        toCurrency = state.currencyToReceive ?: return,
                    )
                    state.copy(
                        currencyForSale = currency,
                        currencyToReceiveAmount = if (saleAmount != 0.0) {
                            conversionResult.fee
                        } else state.currencyToReceiveAmount,
                        currencyForSaleError = null,
                        fee = conversionResult.fee
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
                    val conversionResult = calculateConversionUseCase(
                        amount = amount.toDouble(),
                        fromCurrency = state.currencyForSale ?: return,
                        toCurrency = state.currencyToReceive ?: return,
                    )
                    state.copy(
                        currencyForSaleAmount = amount,
                        currencyForSaleError = null,
                        currencyToReceiveAmount = conversionResult.toReceive,
                        fee = conversionResult.fee
                    )
                }
            }
        }
    }
}