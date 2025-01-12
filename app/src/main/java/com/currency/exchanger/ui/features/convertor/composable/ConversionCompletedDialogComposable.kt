package com.currency.exchanger.ui.features.convertor.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.currency.exchanger.R
import com.currency.exchanger.domain.model.Currency
import com.currency.exchanger.domain.model.CurrencyBalance
import com.currency.exchanger.ui.features.convertor.model.ConversionCompleted
import com.currency.exchanger.ui.theme.CurrencyExchangerTheme

@Composable
fun ConversionCompletedDialog(
    modifier: Modifier = Modifier,
    conversionCompleted: ConversionCompleted,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.currency_converted),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
            )
        },
        text = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(
                    R.string.conversion_completed_description,
                    conversionCompleted.sellCurrencyBalance.convertedUiBalance(),
                    conversionCompleted.receiveCurrencyBalance.convertedUiBalance(),
                    conversionCompleted.fee.convertedUiBalance(),
                ),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.done))
            }
        },
    )
}

@Preview
@Composable
fun ConversionCompletedDialogPreview() {
    CurrencyExchangerTheme {
        ConversionCompletedDialog(
            conversionCompleted = ConversionCompleted(
                sellCurrencyBalance = CurrencyBalance(
                    amount = 100.0,
                    currency = Currency(
                        name = "EUR",
                        rateToBase = 1.0,
                        baseCurrencyName = "EUR"
                    ),
                ),
                receiveCurrencyBalance = CurrencyBalance(
                    amount = 120.0,
                    currency = Currency(
                        name = "USD",
                        rateToBase = 1.1,
                        baseCurrencyName = "EUR"
                    ),
                ),
                fee = CurrencyBalance(
                    amount = 10.0,
                    currency = Currency(
                        name = "EUR",
                        rateToBase = 1.0,
                        baseCurrencyName = "EUR"
                    ),
                ),
            ),
            onDismiss = {},
        )
    }
}