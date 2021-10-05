package com.abdl.mylmk_app.data.source.remote.services

import com.abdl.mylmk_app.data.source.remote.model.GuruResponse
import com.abdl.mylmk_app.login.data.ResultLogin
import com.abdl.mylmk_app.register.data.ResultRegister
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("authapi/login")
    fun login(
        @Field("username") username: String?,
        @Field("password") password: String?
    ): Call<ResultLogin>

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

//    @GET("guruapi")
//    fun getAllGuru(
//        @Field("id_guru") id_guru: String?,
//        @Field("username") username: String?,
//        @Field("nama") nama: String?,
//        @Field("jk") jk: String?,
//        @Field("tempat_lahir") tempat_lahir: String?,
//        @Field("tgl_lahir") tgl_lahir: String?,
//        @Field("alamat") alamat: String?
//    ): Call<List<GuruResponse>>

    @GET("guruapi")
    fun getAllGuru(): Call<GuruResponse>
}