package com.currency.exchanger.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private object AppColor {
    val Primary = Color(0xFF009cdd)
    val OnPrimary = Color(0xFFFFFFFF)
    val OnSecondary = Color(0xFFa0a6ae)
    val OnSurface = Color(0xFF000000)
    val SurfaceVariant = Color(0xFF808080)
}

// Add support of the dark theme if needed
private val LightColorScheme = lightColorScheme(
    primary = AppColor.Primary,
    onPrimary = AppColor.OnPrimary,
    onSecondary = AppColor.OnSecondary,
    onSurface = AppColor.OnSurface,
    surfaceVariant = AppColor.SurfaceVariant
)

@Composable
fun CurrencyExchangerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}