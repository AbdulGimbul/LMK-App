package com.abdl.mylmk_app.di

import android.app.Application
import androidx.room.Room
import com.abdl.mylmk_app.data.source.local.room.LmkDatabase
import com.abdl.mylmk_app.data.source.remote.services.ApiConfig
import com.abdl.mylmk_app.data.source.remote.services.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): LmkDatabase =
        Room.databaseBuilder(app, LmkDatabase::class.java, "MyDatabase.db")
            .build()

}