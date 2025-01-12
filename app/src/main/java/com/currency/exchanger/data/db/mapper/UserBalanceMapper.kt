package com.currency.exchanger.data.db.mapper

import com.currency.exchanger.data.db.entity.CurrencyBalanceEntity
import com.currency.exchanger.data.db.entity.UserBalanceEntity
import com.currency.exchanger.domain.model.UserBalance
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun UserBalance.toEntity() = UserBalanceEntity(
    currencyBalanceListJson = Gson().toJson(this.currencyBalanceList.map { it.toEntity() }),
)

fun UserBalanceEntity.toDomain(): UserBalance {
    val currencyBalanceList = Gson().fromJson<List<CurrencyBalanceEntity>>(
        this.currencyBalanceListJson,
        object : TypeToken<List<CurrencyBalanceEntity>>() {}.type
    )
    return UserBalance(currencyBalanceList = currencyBalanceList.map { it.toDomain() })
}