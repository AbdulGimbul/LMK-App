package com.abdl.mylmk_app.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0

@Entity
data class UserEntity(
    var id_user: Int? = null,
    var nama: String? = null,
    var username: String? = null,
    var jk: String? = null,
    var alamat: String? = null,
    var nohp: Int? = null,
    var avatar: String? = null,
    var password: String? = null,
    var salt: String? = null,
    var role: Int? = null
) {
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}