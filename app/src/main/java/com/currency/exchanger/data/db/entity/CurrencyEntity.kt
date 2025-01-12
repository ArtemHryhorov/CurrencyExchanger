package com.currency.exchanger.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrencyEntity(
    @PrimaryKey val name: String,
    val rateToBase: Double,
    val baseCurrencyName: String
)