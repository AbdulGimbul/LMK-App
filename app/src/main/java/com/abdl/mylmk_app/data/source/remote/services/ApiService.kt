package com.abdl.mylmk_app.data.source.remote.services

import com.abdl.mylmk_app.data.source.remote.model.AuthResponse
import com.abdl.mylmk_app.data.source.remote.model.GuruResponse
import com.abdl.mylmk_app.data.source.remote.model.JadwalUserResponse
import com.abdl.mylmk_app.data.source.remote.model.ProgramResponse
import com.abdl.mylmk_app.register.data.ResultRegister
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("authapi/login")
    suspend fun userLogin(
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("authapi/register")
    fun register(
        @Field("nik") nik: String?,
        @Field("nama") nama: String?,
        @Field("username") username: String?,
        @Field("jk") jk: String?,
        @Field("alamat") alamat: String?,
        @Field("password") password: String?,
        @Field("repeatPassword") repeatPassword: String?
    ): Call<ResultRegister>

    @GET("guruapi")
    fun getAllGuru(): Call<GuruResponse>

    @GET("infoapi/getprogram")
    fun getAllProgram(): Call<ProgramResponse>

    @GET("jadwalapi/getjadwaluser")
    fun getJadwalUser(): Call<JadwalUserResponse>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): ApiService {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://192.168.0.104/project-lmk/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}