package com.currency.exchanger.ui.convertor.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.currency.exchanger.R
import com.currency.exchanger.domain.extensions.formatAmountWithDecimals
import com.currency.exchanger.domain.model.Currency
import com.currency.exchanger.ui.theme.Color
import com.currency.exchanger.ui.theme.CurrencyExchangerTheme

private enum class CurrencyOperationType {
    SELL,
    RECEIVE,
}

@Composable
fun SellCurrencyDropDown(
    modifier: Modifier = Modifier,
    currency: Currency,
    amount: Double,
    currencies: List<Currency>,
    onSelected: (Currency) -> Unit,
) {
    CurrencyDropDown(
        modifier = modifier,
        currency = currency,
        amount = amount,
        currencyOperationType = CurrencyOperationType.SELL,
        currencies = currencies,
        onSelected = onSelected,
    )
}

@Composable
fun ReceiveCurrencyDropDown(
    modifier: Modifier = Modifier,
    currency: Currency,
    amount: Double,
    currencies: List<Currency>,
    onSelected: (Currency) -> Unit,
) {
    CurrencyDropDown(
        modifier = modifier,
        currency = currency,
        amount = amount,
        currencyOperationType = CurrencyOperationType.RECEIVE,
        currencies = currencies,
        onSelected = onSelected,
    )
}

@Composable
private fun CurrencyDropDown(
    modifier: Modifier = Modifier,
    currency: Currency,
    amount: Double,
    currencyOperationType: CurrencyOperationType,
    currencies: List<Currency>,
    onSelected: (Currency) -> Unit,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (currencyOperationType == CurrencyOperationType.SELL) {
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(R.drawable.ic_sell_currency),
                contentDescription = null,
            )
        } else {
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(R.drawable.ic_receive_currency),
                contentDescription = null,
            )
        }
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp),
            text = if (currencyOperationType == CurrencyOperationType.SELL) {
                stringResource(R.string.sell)
            } else {
                stringResource(R.string.receive)
            },
            color = Color.TextOnSurface,
            textAlign = TextAlign.Start,
        )
        if (currencyOperationType == CurrencyOperationType.SELL) {
            Text(
                text = amount.formatAmountWithDecimals(2),
                color = Color.TextOnSurface
            )
        } else {
            Text(
                text = "+${amount.formatAmountWithDecimals(2)}",
                color = Color.Profit,
            )
        }
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = currency.name,
            color = Color.TextOnSurface
        )
        CurrencyDropdownMenu(
            currencies = currencies,
            onSelected = onSelected,
        )
    }
}

@Preview
@Composable
fun SellCurrencyDropDownPreview() {
    CurrencyExchangerTheme {
        Column {
            SellCurrencyDropDown(
                currency = Currency(
                    name = "EUR",
                    rateToBase = 1.0,
                    baseCurrencyName = "EUR",
                ),
                amount = 100.0,
                currencies = emptyList(),
                onSelected = {},
            )
            ReceiveCurrencyDropDown(
                currency = Currency(
                    name = "EUR",
                    rateToBase = 1.0,
                    baseCurrencyName = "EUR",
                ),
                amount = 100.0,
                currencies = emptyList(),
                onSelected = {},
            )
        }
    }
}