package com.currency.exchanger.ui.convertor.composable

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.currency.exchanger.domain.CurrencyBalance
import com.currency.exchanger.ui.theme.Color

@Composable
fun CurrencyBalanceItemC(item: CurrencyBalance) {
    Text(
        text = item.convertedUiBalance(),
        color = Color.TextOnSurface,
    )
}