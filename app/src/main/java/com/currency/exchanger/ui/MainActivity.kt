package com.currency.exchanger.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.currency.exchanger.ui.features.convertor.ConvertorScreen
import com.currency.exchanger.ui.features.convertor.ConvertorViewModel
import com.currency.exchanger.ui.theme.CurrencyExchangerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ConvertorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyExchangerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ConvertorScreen(
                        modifier = Modifier.padding(innerPadding),
                        state = viewModel.convertorState.collectAsState().value,
                        onEvent = viewModel::onEvent,
                    )
                }
            }
        }
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
        }
    }
}