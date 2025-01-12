package com.currency.exchanger.data.db.dao

import androidx.room.*
import com.currency.exchanger.data.db.entity.UserBalanceEntity

@Dao
interface UserBalanceDao {

    @Query("SELECT * FROM UserBalanceEntity LIMIT 1")
    suspend fun getUserBalance(): UserBalanceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserBalance(userBalance: UserBalanceEntity)
}
