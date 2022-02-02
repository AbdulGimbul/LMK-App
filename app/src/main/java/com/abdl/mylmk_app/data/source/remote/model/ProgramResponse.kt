package com.abdl.mylmk_app.data.source.remote.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ProgramResponse(

    @field:SerializedName("program")
    val program: List<ProgramItem>
)

@Entity(tableName = "tbl_program")
@Parcelize
data class ProgramItem(
    @field:SerializedName("id_info")
    @PrimaryKey val idInfo: String,

    @field:SerializedName("judul")
    val judul: String,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("gambar")
    val gambar: String,

    @field:SerializedName("type")
    val type: String,

    var expand: Boolean = false
) : Parcelable
