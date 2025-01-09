package com.currency.exchanger.ui.convertor.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.currency.exchanger.domain.CurrencyBalance

@Composable
fun CurrencyBalanceList(
    items: List<CurrencyBalance>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        items(items) { item -> CurrencyBalanceItemC(item) }
    }
}
