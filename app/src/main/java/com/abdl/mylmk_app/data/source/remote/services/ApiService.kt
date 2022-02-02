package com.abdl.mylmk_app.data.source.remote.services

import com.abdl.mylmk_app.data.source.remote.model.*
import com.abdl.mylmk_app.register.data.ResultRegister
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        @Field("no_hp") no_hp: String?,
        @Field("password") password: String?,
        @Field("repeatPassword") repeatPassword: String?
    ): Call<ResultRegister>

    @FormUrlEncoded
    @PUT("userapi/{id}")
    fun updateProfile(
        @Path("id") idUser: Int?,
        @Field("nama") name: String?,
        @Field("username") username: String?,
        @Field("alamat") alamat: String?,
        @Field("nohp") no_hp: String?
    ): Call<ResultStatus>

    @Multipart
    @POST("userapi/updatefoto/{id}")
    fun uploadImage(
        @Path("id") idUser: Int?,
        @Part image: MultipartBody.Part,
        @Part("_method") _method: RequestBody
    ): Call<UploadResponse>

    @GET("guruapi")
    suspend fun getAllGuru(): GuruResponse

    @GET("infoapi/getprogram")
    suspend fun getAllProgram(): ProgramResponse

    @GET("jadwalapi/getjadwaluser/{id_user}")
    suspend fun getJadwalUser(
        @Path("id_user") idUser: Int
    ): JadwalResponse

    @GET("jadwalapi/getjadwalguru/{id_guru}")
    suspend fun getJadwalGuru(
        @Path("id_guru") idGuru: Int
    ): JadwalResponse
}