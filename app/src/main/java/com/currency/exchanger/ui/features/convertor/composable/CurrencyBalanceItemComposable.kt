package com.currency.exchanger.ui.features.convertor.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.currency.exchanger.domain.model.Currency
import com.currency.exchanger.domain.model.CurrencyBalance
import com.currency.exchanger.ui.theme.CurrencyExchangerTheme

@Composable
fun CurrencyBalanceItem(item: CurrencyBalance) {
    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = item.convertedUiBalance(),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.SemiBold
        ),
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