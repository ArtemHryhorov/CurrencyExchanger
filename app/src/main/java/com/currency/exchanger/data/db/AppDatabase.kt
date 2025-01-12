package com.currency.exchanger.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.currency.exchanger.data.db.dao.UserBalanceDao
import com.currency.exchanger.data.db.entity.CurrencyBalanceEntity
import com.currency.exchanger.data.db.entity.CurrencyEntity
import com.currency.exchanger.data.db.entity.UserBalanceEntity

@Database(
    entities = [
        UserBalanceEntity::class,
        CurrencyBalanceEntity::class,
        CurrencyEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userBalanceDao(): UserBalanceDao
}