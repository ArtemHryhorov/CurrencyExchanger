package com.currency.exchanger.data.di

import android.content.Context
import androidx.room.Room
import com.currency.exchanger.data.db.AppDatabase
import com.currency.exchanger.data.db.dao.UserBalanceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCurrencyDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "currency_exchanger_app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserBalanceDao(database: AppDatabase): UserBalanceDao = database.userBalanceDao()
}
