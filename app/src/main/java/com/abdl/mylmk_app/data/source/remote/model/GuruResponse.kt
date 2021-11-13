package com.abdl.mylmk_app.data.source.remote.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


data class GuruResponse(
    var guru: List<GuruItem>
)

@Entity(tableName = "tbl_guru")
@Parcelize
data class GuruItem(
    @PrimaryKey var id_guru: String,
    var username: String,
    var nama: String,
    var jk: String,
    var tempat_lahir: String,
    var tgl_lahir: String,
    var alamat: String,
    var avatar: String,
) : Parcelable