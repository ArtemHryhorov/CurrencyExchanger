package com.currency.exchanger.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Color.Primary,
    secondary = Color.PurpleGrey40,
    tertiary = Color.Pink40,
    onPrimary = Color.TextPrimary,
    onSecondary = Color.TextSecondary,
    onSurface = Color.TextOnSurface,
)

@Composable
fun CurrencyExchangerTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}