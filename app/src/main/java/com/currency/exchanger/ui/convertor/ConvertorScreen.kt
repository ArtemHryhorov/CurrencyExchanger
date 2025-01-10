package com.currency.exchanger.ui.convertor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import com.currency.exchanger.ui.convertor.composable.CurrencyBalanceList
import com.currency.exchanger.ui.convertor.composable.ReceiveCurrencyDropDown
import com.currency.exchanger.ui.convertor.composable.SellCurrencyDropDown
import com.currency.exchanger.ui.convertor.composable.TabBar
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding(),
    ) {
        TabBar(
            title = stringResource(R.string.currency_convertor),
            modifier = Modifier.fillMaxWidth(),
        )
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Text(
                text = stringResource(R.string.my_balances).uppercase(),
                color = MaterialTheme.colorScheme.secondary,
            )
            CurrencyBalanceList(
                items = state.userBalance.currencyBalanceList,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            Text(
                text = stringResource(R.string.currency_exchange).uppercase(),
                color = MaterialTheme.colorScheme.secondary,
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
                    onCurrencySelected = { currency ->
                        onEvent(ConvertorEvent.OnSellCurrencySelected(currency))
                    },
                    onAmountChanged = { amount ->
                        onEvent(ConvertorEvent.OnSellAmountChanged(amount))
                    }
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
                    amount = state.currencyToReceiveAmount,
                    currencies = state.allCurrencies,
                    onCurrencySelected = { currency ->
                        onEvent(ConvertorEvent.OnReceiveCurrencySelected(currency))
                    },
                    onAmountChanged = { amount ->
                        onEvent(ConvertorEvent.OnReceiveAmountChanged(amount))
                    }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(size = 24.dp),
                    ),
                onClick = {

                }
            ) {
                Text(
                    text = stringResource(R.string.submit).uppercase(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(all = 4.dp),
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
                    name = "EUR",
                    rateToBase = 1.0,
                    baseCurrencyName = "EUR"
                ),
                currencyToReceive = Currency(
                    name = "USD",
                    rateToBase = 1.1,
                    baseCurrencyName = "EUR"
                ),
                currencyForSaleAmount = "150.00",
                currencyToReceiveAmount = "160.00",
            ),
            onEvent = {},
        )
    }
}