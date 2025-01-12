package com.currency.exchanger.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserBalanceEntity(
    @PrimaryKey val id: Long = 1,
    val currencyBalanceListJson: String,
)