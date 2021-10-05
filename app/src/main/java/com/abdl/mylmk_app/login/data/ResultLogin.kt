package com.abdl.mylmk_app.login.data

import com.google.gson.annotations.SerializedName

data class ResultLogin(
    @field:SerializedName("user")
    val user: User? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)