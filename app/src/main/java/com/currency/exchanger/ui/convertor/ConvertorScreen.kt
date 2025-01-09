package com.currency.exchanger.ui.convertor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.currency.exchanger.R
import com.currency.exchanger.domain.UserBalance
import com.currency.exchanger.ui.convertor.composable.CurrencyBalanceList
import com.currency.exchanger.ui.core.composable.TabBar
import com.currency.exchanger.ui.theme.Color
import com.currency.exchanger.ui.theme.CurrencyExchangerTheme

@Composable
fun ConvertorScreen(
    modifier: Modifier = Modifier,
    state: ConvertorState,
    onEvent: (ConvertorEvent) -> Unit,
) {
    LaunchedEffect("Initial loading") {
        onEvent(ConvertorEvent.LoadUserBalance)
    }

    Column(modifier = modifier) {
        TabBar(
            title = stringResource(R.string.currency_convertor),
            modifier = Modifier.fillMaxWidth(),
        )
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Text(
                text = stringResource(R.string.my_balances).uppercase(),
                color = Color.TextSecondary,
            )
            CurrencyBalanceList(
                items = state.userBalance.currencyBalanceList,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConvertorScreenPreview() {
    CurrencyExchangerTheme {
        ConvertorScreen(
            state = ConvertorState(userBalance = UserBalance.createNew()),
            onEvent = {},
        )
    }
}