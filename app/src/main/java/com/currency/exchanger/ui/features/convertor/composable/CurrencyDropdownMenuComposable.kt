package com.currency.exchanger.ui.features.convertor.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.currency.exchanger.domain.model.Currency

@Composable
fun CurrencyDropdownMenu(
    modifier: Modifier = Modifier,
    currencies: List<Currency>,
    onSelected: (Currency) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.wrapContentSize(Alignment.Center)) {
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "Dropdown Menu",
            modifier = Modifier
                .clickable { expanded = true }
                .padding(16.dp)
                .size(24.dp)
        )

        DropdownMenu(
            modifier = Modifier.heightIn(max = 400.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            currencies.forEach { currency ->
                Text(
                    text = currency.name,
                    modifier = Modifier
                        .clickable {
                            expanded = false
                            onSelected(currency)
                        }
                        .padding(horizontal = 16.dp)
                        .padding(vertical = 6.dp)
                )
            }
        }
    }
}
