package com.currency.exchanger.ui.features.convertor.composable

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.currency.exchanger.domain.common.regex.AppRegex
import com.currency.exchanger.ui.features.convertor.validation.ValidationError
import com.currency.exchanger.ui.theme.CurrencyExchangerTheme

@Composable
fun AmountTextField(
    modifier: Modifier = Modifier,
    value: String,
    onChanged: (String) -> Unit,
    isFocusedInitially: Boolean = false,
    error: ValidationError? = null,
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect("FocusRequester") {
        if (isFocusedInitially) focusRequester.requestFocus()
    }

    OutlinedTextField(
        value = value,
        onValueChange = {
            if (error == null) {
                val sanitizedInput = sanitizeInput(it)
                onChanged(sanitizedInput)
            } else onChanged(it)
        },
        modifier = modifier.focusRequester(focusRequester),
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
        ),
        isError = error != null,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.primary,
        ),
        supportingText = {
            error?.let {
                Text(
                    text = stringResource(error.uiError.errorMessageRes),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    )
}

private fun sanitizeInput(input: String): String {
    return if (AppRegex.validAmountWithTwoDigits.matches(input).not()) {
        val parts = input.split(".")
        if (parts.size >= 2) "${parts[0]}.${parts[1].take(2)}" else input
    } else input
}

@Preview
@Composable
fun AmountTextFieldPreview() {
    CurrencyExchangerTheme {
        AmountTextField(
            value = "150.00",
            onChanged = {},
        )
    }
}