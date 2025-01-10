package com.currency.exchanger.ui.convertor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.exchanger.domain.model.Currency
import com.currency.exchanger.domain.model.UserBalance
import com.currency.exchanger.domain.usecase.GetAllCurrencyUseCase
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
) : ViewModel() {

    private val _convertorState = MutableStateFlow(ConvertorState())
    val convertorState = _convertorState.asStateFlow()

    fun onEvent(event: ConvertorEvent) {
        when (event) {
            ConvertorEvent.LoadUserBalance -> loadUserBalance()
            ConvertorEvent.Submit -> convertCurrencies()
            is ConvertorEvent.OnReceiveCurrencySelected -> onReceiveCurrencySelected(event.currency)
            is ConvertorEvent.OnReceiveAmountChanged -> onReceiveAmountChanged(event.amount)
            is ConvertorEvent.OnSellCurrencySelected -> onSellCurrencySelected(event.currency)
            is ConvertorEvent.OnSellAmountChanged ->onSellAmountChanged(event.amount)
        }
    }

    private fun loadUserBalance() {
        viewModelScope.launch(Dispatchers.IO) {
            val allCurrencies = getAllCurrencyUseCase()
            _convertorState.update { convertorState ->
                convertorState.copy(
                    userBalance = UserBalance.createNew(),
                    currencyForSale = allCurrencies.first(),
                    currencyToReceive = allCurrencies.last(),
                    allCurrencies = allCurrencies,
                )
            }
        }
    }

    private fun convertCurrencies() {

    }

    private fun onReceiveCurrencySelected(currency: Currency) {
        _convertorState.update { convertorState ->
            convertorState.copy(currencyToReceive = currency)
        }
    }

    private fun onReceiveAmountChanged(amount: String) {
        _convertorState.update { convertorState ->
            convertorState.copy(currencyToReceiveAmount = amount)
        }
    }

    private fun onSellCurrencySelected(currency: Currency) {
        _convertorState.update { convertorState ->
            convertorState.copy(currencyForSale = currency)
        }
    }

    private fun onSellAmountChanged(amount: String) {
        _convertorState.update { convertorState ->
            convertorState.copy(currencyForSaleAmount = amount)
        }
    }
}