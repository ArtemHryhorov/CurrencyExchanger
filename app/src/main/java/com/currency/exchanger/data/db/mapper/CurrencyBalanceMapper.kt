package com.currency.exchanger.data.db.mapper

import com.currency.exchanger.data.db.entity.CurrencyBalanceEntity
import com.currency.exchanger.domain.model.CurrencyBalance

fun CurrencyBalance.toEntity() = CurrencyBalanceEntity(
    amount = this.amount,
    currency = this.currency.toEntity()
)

fun CurrencyBalanceEntity.toDomain() = CurrencyBalance(
    amount = this.amount,
    currency = this.currency.toDomain()
)