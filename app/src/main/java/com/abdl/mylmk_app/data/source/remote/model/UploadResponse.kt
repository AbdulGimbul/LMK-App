package com.abdl.mylmk_app.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("messages")
    val message: String,

    @field:SerializedName("image")
    val image: String?
)