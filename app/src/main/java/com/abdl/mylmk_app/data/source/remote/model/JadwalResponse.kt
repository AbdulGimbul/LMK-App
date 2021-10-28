package com.abdl.mylmk_app.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class JadwalUserResponse(

    @field:SerializedName("Jadwal User")
    val jadwalUser: List<JadwalUserItem>
)

data class JadwalUserItem(

    @field:SerializedName("hari")
    val hari: String,

    @field:SerializedName("nama_murid")
    val namaMurid: String,

    @field:SerializedName("id_jadwal")
    val idJadwal: String,

    @field:SerializedName("jam")
    val jam: String,

    @field:SerializedName("alamat_murid")
    val alamatMurid: String,

    @field:SerializedName("nama_guru")
    val namaGuru: String,

    @field:SerializedName("id_murid")
    val idMurid: String
)
