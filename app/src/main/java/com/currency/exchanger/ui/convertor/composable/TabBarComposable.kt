package com.currency.exchanger.ui.convertor.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.currency.exchanger.ui.theme.Color
import com.currency.exchanger.ui.theme.CurrencyExchangerTheme

@Composable
fun TabBar(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color = Color.Primary)
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = title,
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            color = Color.TextPrimary,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TabBarPreview() {
    CurrencyExchangerTheme {
        TabBar(title = "Currency convertor")
    }
}