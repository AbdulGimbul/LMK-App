package com.abdl.mylmk_app.data.source.remote.model

import com.google.gson.annotations.SerializedName

class ResultStatus {
    @field:SerializedName("messages")
    val message: String? = null

    @field:SerializedName("status")
    val status: Int? = null
}