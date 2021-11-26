package com.abdl.mylmk_app.data.source.remote.services

import com.abdl.mylmk_app.data.source.remote.model.AuthResponse
import com.abdl.mylmk_app.data.source.remote.model.GuruResponse
import com.abdl.mylmk_app.data.source.remote.model.JadwalResponse
import com.abdl.mylmk_app.data.source.remote.model.ProgramResponse
import com.abdl.mylmk_app.register.data.ResultRegister
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

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
        @Field("nama") nama: String?,
        @Field("username") username: String?,
        @Field("jk") jk: String?,
        @Field("alamat") alamat: String?,
        @Field("password") password: String?,
        @Field("repeatPassword") repeatPassword: String?
    ): Call<ResultRegister>

    @GET("guruapi")
    suspend fun getAllGuru(): GuruResponse

    @GET("infoapi/getprogram")
    fun getAllProgram(): Call<ProgramResponse>

    @GET("jadwalapi/getjadwaluser/{id_user}")
    suspend fun getJadwalUser(
        @Path("id_user") idUser: Int
    ): JadwalResponse

    @GET("jadwalapi/getjadwalguru/{id_guru}")
    suspend fun getJadwalGuru(
        @Path("id_guru") idGuru: Int
    ): JadwalResponse
}