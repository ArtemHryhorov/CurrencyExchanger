package com.currency.exchanger.ui.convertor.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.currency.exchanger.domain.model.Currency
import com.currency.exchanger.domain.model.CurrencyBalance
import com.currency.exchanger.ui.theme.CurrencyExchangerTheme

@Composable
fun CurrencyBalanceList(
    items: List<CurrencyBalance>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        items(items) { item -> CurrencyBalanceItem(item) }
    }
}

@Preview
@Composable
fun CurrencyBalanceListPreview() {
    CurrencyExchangerTheme {
        CurrencyBalanceList(
            items = listOf(
                CurrencyBalance(
                    amount = 100.0,
                    currency = Currency(
                        name = "EUR",
                        rateToBase = 1.0,
                        baseCurrencyName = "EUR",
                    ),
                ),
                CurrencyBalance(
                    amount = 59.7,
                    currency = Currency(
                        name = "USD",
                        rateToBase = 1.1,
                        baseCurrencyName = "EUR",
                    ),
                ),
            ),
        )
    }
}
