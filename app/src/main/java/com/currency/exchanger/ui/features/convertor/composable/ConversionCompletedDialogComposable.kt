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
import com.currency.exchanger.R
import com.currency.exchanger.ui.features.convertor.model.ConversionCompleted

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