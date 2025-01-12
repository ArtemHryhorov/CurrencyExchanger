package com.currency.exchanger.ui.common.composable

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
import com.currency.exchanger.ui.common.error.ErrorType
import com.currency.exchanger.ui.theme.CurrencyExchangerTheme

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    errorType: ErrorType,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        text = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(errorType.uiError.errorMessageRes),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.ok))
            }
        },
    )
}

@Preview
@Composable
fun ErrorDialogNoInternetPreview() {
    CurrencyExchangerTheme {
        ErrorDialog(
            errorType = ErrorType.NO_INTERNET_CONNECTION,
            onDismiss = {},
        )
    }
}

@Preview
@Composable
fun ErrorDialogUnknownPreview() {
    CurrencyExchangerTheme {
        ErrorDialog(
            errorType = ErrorType.UNKNOWN,
            onDismiss = {},
        )
    }
}