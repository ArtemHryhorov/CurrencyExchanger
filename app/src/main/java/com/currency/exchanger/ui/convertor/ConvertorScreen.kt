package com.currency.exchanger.ui.convertor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.currency.exchanger.R
import com.currency.exchanger.domain.model.Currency
import com.currency.exchanger.domain.model.UserBalance
import com.currency.exchanger.ui.convertor.composable.ReceiveCurrencyDropDown
import com.currency.exchanger.ui.convertor.composable.CurrencyBalanceList
import com.currency.exchanger.ui.convertor.composable.SellCurrencyDropDown
import com.currency.exchanger.ui.convertor.composable.TabBar
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

    Column(modifier = modifier.fillMaxSize()) {
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
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            Text(
                text = stringResource(R.string.currency_exchange).uppercase(),
                color = Color.TextSecondary,
                modifier = Modifier.padding(top = 16.dp),
            )
            state.currencyForSale?.let { currencyForSale ->
                SellCurrencyDropDown(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    currency = currencyForSale,
                    amount = state.currencyForSaleAmount,
                    currencies = state.allCurrencies,
                    onSelected = { currency ->
                        onEvent(ConvertorEvent.OnSellCurrencySelected(currency))
                    },
                )
            }
            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .padding(start = 48.dp)
            )
            state.currencyToReceive?.let { currencyToReceive ->
                ReceiveCurrencyDropDown(
                    modifier = Modifier.fillMaxWidth(),
                    currency = currencyToReceive,
                    amount = state.currencyForSaleAmount,
                    currencies = state.allCurrencies,
                    onSelected = { currency ->
                        onEvent(ConvertorEvent.OnReceiveCurrencySelected(currency))
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConvertorScreenPreview() {
    CurrencyExchangerTheme {
        ConvertorScreen(
            state = ConvertorState(
                userBalance = UserBalance.createNew(),
                currencyForSale = Currency(
                    name = "USD",
                    rateToBase = 1.1,
                    baseCurrencyName = "EUR"
                ),
                currencyForSaleAmount = 150.0,
            ),
            onEvent = {},
        )
    }
}