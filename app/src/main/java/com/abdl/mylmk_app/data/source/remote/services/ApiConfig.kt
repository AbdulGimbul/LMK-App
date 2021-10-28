package com.abdl.mylmk_app.data.source.remote.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    //    val BASE_URL: String = "http://abdl.alterdev.id/"
    val BASE_URL: String = "http://192.168.0.104/project-lmk/public/"
//    val BASE_URL: String = "http://192.168.100.29/project-lmk/public/"
//    val BASE_URL: String = "http://192.168.0.104/project-lmk/public/"
//    val BASE_URL: String = "http://172.27.169.60/project-lmk/public/"

    val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    val client: OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
    }.build()

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getService() = getRetrofit().create(ApiService::class.java)
}