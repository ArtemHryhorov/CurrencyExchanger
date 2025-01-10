package com.currency.exchanger.ui.features.convertor.composable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.currency.exchanger.domain.model.Currency
import com.currency.exchanger.domain.model.CurrencyBalance
import com.currency.exchanger.ui.theme.CurrencyExchangerTheme

@Composable
fun CurrencyBalanceItem(item: CurrencyBalance) {
    Text(
        text = item.convertedUiBalance(),
        color = MaterialTheme.colorScheme.onSurface,
    )
}

@Preview
@Composable
fun CurrencyBalanceItemPreview() {
    CurrencyExchangerTheme {
        CurrencyBalanceItem(
            item = CurrencyBalance(
                amount = 100.0,
                currency = Currency(
                    name = "EUR",
                    rateToBase = 1.0,
                    baseCurrencyName = "EUR",
                ),
            ),
        )
    }
}