package com.currency.exchanger.data.db.mapper

import com.currency.exchanger.data.db.entity.CurrencyEntity
import com.currency.exchanger.domain.model.Currency

fun Currency.toEntity() = CurrencyEntity(
    name = this.name,
    rateToBase = this.rateToBase,
    baseCurrencyName = this.baseCurrencyName
)

fun CurrencyEntity.toDomain() = Currency(
    name = this.name,
    rateToBase = this.rateToBase,
    baseCurrencyName = this.baseCurrencyName
)