package com.abdl.mylmk_app.login.data

import com.google.gson.annotations.SerializedName

data class User(
    @field:SerializedName("id_user")
    val userId: String? = null,

    @field:SerializedName("nik")
    val nik: Int? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("jk")
    val jk: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("password")
    val password: String? = null,
)
