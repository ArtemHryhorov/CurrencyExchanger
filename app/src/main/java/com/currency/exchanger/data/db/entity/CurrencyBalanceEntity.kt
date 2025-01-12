package com.currency.exchanger.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Embedded

@Entity
data class CurrencyBalanceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    @Embedded val currency: CurrencyEntity
)