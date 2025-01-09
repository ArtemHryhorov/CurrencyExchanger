package com.currency.exchanger.ui.convertor

import androidx.lifecycle.ViewModel
import com.currency.exchanger.domain.UserBalance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ConvertorViewModel @Inject constructor() : ViewModel() {

    private val _convertorState: MutableStateFlow<ConvertorState> = MutableStateFlow(ConvertorState())
    val convertorState = _convertorState.asStateFlow()

    fun onEvent(convertorEvent: ConvertorEvent) {
        when (convertorEvent) {
            ConvertorEvent.LoadUserBalance -> loadUserBalance()
        }
    }

    private fun loadUserBalance() {
        // TODO("Replace with UseCase call")
        _convertorState.update { convertorState ->
            convertorState.copy(
                userBalance = UserBalance.createNew()
            )
        }
    }
}