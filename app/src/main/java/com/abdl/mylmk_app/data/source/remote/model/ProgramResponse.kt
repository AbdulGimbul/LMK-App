package com.abdl.mylmk_app.data.source.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ProgramResponse(

    @field:SerializedName("program")
    val program: List<ProgramItem>
)

@Parcelize
data class ProgramItem(

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("judul")
    val judul: String,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("id_info")
    val idInfo: String,

    @field:SerializedName("gambar")
    val gambar: String
) : Parcelable
