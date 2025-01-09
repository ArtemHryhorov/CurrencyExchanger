package com.currency.exchanger.data.di

import com.currency.exchanger.AppConstants
import com.currency.exchanger.data.api.CurrencyApiService
import com.currency.exchanger.data.repository.CurrencyRepositoryImpl
import com.currency.exchanger.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import dagger.Binds

@Module
@InstallIn(SingletonComponent::class)
fun interface NetworkModule {

    @Binds
    @Singleton
    fun bindCurrencyRepository(
        currencyRepositoryImpl: CurrencyRepositoryImpl,
    ): CurrencyRepository

    companion object {
        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideCurrencyApiService(retrofit: Retrofit): CurrencyApiService {
            return retrofit.create(CurrencyApiService::class.java)
        }
    }
}