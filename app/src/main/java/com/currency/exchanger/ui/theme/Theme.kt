package com.currency.exchanger.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private object AppColor {
    val Primary = Color(0xFF009cdd)
    val TextPrimary = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFFa0a6ae)
    val TextOnSurface = Color(0xFF000000)
}

// Add support of the dark theme if needed
private val LightColorScheme = lightColorScheme(
    primary = AppColor.Primary,
    onPrimary = AppColor.TextPrimary,
    onSecondary = AppColor.TextSecondary,
    onSurface = AppColor.TextOnSurface,
)

@Composable
fun CurrencyExchangerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}