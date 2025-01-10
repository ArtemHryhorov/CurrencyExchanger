package com.currency.exchanger.ui.features.convertor.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
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
import com.currency.exchanger.domain.model.Currency
import com.currency.exchanger.ui.common.ValidationError
import com.currency.exchanger.ui.theme.CurrencyExchangerTheme

private enum class CurrencyOperationType {
    SELL,
    RECEIVE,
}

@Composable
fun SellCurrencyDropDown(
    modifier: Modifier = Modifier,
    currency: Currency,
    amount: String?,
    currencies: List<Currency>,
    error: ValidationError? = null,
    onCurrencySelected: (Currency) -> Unit,
    onAmountChanged: (String) -> Unit,
) {
    CurrencyDropDown(
        modifier = modifier,
        currency = currency,
        amount = amount,
        currencyOperationType = CurrencyOperationType.SELL,
        currencies = currencies,
        error = error,
        onCurrencySelected = onCurrencySelected,
        onAmountChanged = onAmountChanged,
    )
}

@Composable
fun ReceiveCurrencyDropDown(
    modifier: Modifier = Modifier,
    currency: Currency,
    amount: String?,
    currencies: List<Currency>,
    error: ValidationError? = null,
    onCurrencySelected: (Currency) -> Unit,
    onAmountChanged: (String) -> Unit,
) {
    CurrencyDropDown(
        modifier = modifier,
        currency = currency,
        amount = amount,
        currencyOperationType = CurrencyOperationType.RECEIVE,
        currencies = currencies,
        error = error,
        onCurrencySelected = onCurrencySelected,
        onAmountChanged = onAmountChanged,
    )
}

@Composable
private fun CurrencyDropDown(
    modifier: Modifier = Modifier,
    currency: Currency,
    amount: String?,
    currencyOperationType: CurrencyOperationType,
    currencies: List<Currency>,
    error: ValidationError? = null,
    onCurrencySelected: (Currency) -> Unit,
    onAmountChanged: (String) -> Unit,
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
            modifier = Modifier.padding(start = 4.dp),
            text = if (currencyOperationType == CurrencyOperationType.SELL) {
                stringResource(R.string.sell)
            } else {
                stringResource(R.string.receive)
            },
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Start,
        )
        if (currencyOperationType == CurrencyOperationType.SELL) {
            AmountTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                value = amount ?: "",
                isFocusedInitially = true,
                error = error,
                onChanged = onAmountChanged,
            )
        } else {
            // TODO("Add color customization")
            AmountTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                value = amount ?: "",
                error = error,
                onChanged = onAmountChanged,
            )
        }
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = currency.name,
            color = MaterialTheme.colorScheme.onSurface
        )
        CurrencyDropdownMenu(
            currencies = currencies,
            onSelected = onCurrencySelected,
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
                amount = "100.0",
                currencies = emptyList(),
                onCurrencySelected = {},
                onAmountChanged = {},
            )
            ReceiveCurrencyDropDown(
                currency = Currency(
                    name = "EUR",
                    rateToBase = 1.0,
                    baseCurrencyName = "EUR",
                ),
                amount = "100.0",
                currencies = emptyList(),
                onCurrencySelected = {},
                onAmountChanged = {},
            )
        }
    }
}