package com.abdl.mylmk_app.register.data

import com.google.gson.annotations.SerializedName

data class ResultRegister(
    @field:SerializedName("messages")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)