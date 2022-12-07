package com.abdl.mylmk_app.data.source.remote.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class JadwalResponse(

    @field:SerializedName("Jadwal User")
    val jadwalUser: List<JadwalUserItem>,

    @field:SerializedName("Jadwal Guru")
    val jadwalGuru: List<JadwalGuruItem>
)

@Entity(tableName = "tbl_jadwal")
@Parcelize
data class JadwalUserItem(

    @field:SerializedName("hari")
    val hari: String,

    @field:SerializedName("nama_murid")
    val namaMurid: String,

    @field:SerializedName("id_jadwal")
    @PrimaryKey val idJadwal: String,

    @field:SerializedName("jam")
    val jam: String,

    @field:SerializedName("alamat_murid")
    val alamatMurid: String,

    @field:SerializedName("nama_guru")
    val namaGuru: String,

    @field:SerializedName("id_murid")
    val idMurid: String,

    @field:SerializedName("id_guru")
    val idGuru: String,

    @field:SerializedName("alamat_guru")
    val alamatGuru: String,

    @field:SerializedName("avatar_guru")
    val avatarGuru: String
) : Parcelable

@Entity(tableName = "tbl_jadwal_guru")
data class JadwalGuruItem(

    @field:SerializedName("hari")
    val hari: String,

    @field:SerializedName("nama_murid")
    val namaMurid: String,

    @field:SerializedName("id_jadwal")
    @PrimaryKey val idJadwal: String,

    @field:SerializedName("jam")
    val jam: String,

    @field:SerializedName("id_guru")
    val idGuru: String,

    @field:SerializedName("nama_guru")
    val namaGuru: String,

    @field:SerializedName("id_murid")
    val idMurid: String,

    @field:SerializedName("status")
    val status: String
)