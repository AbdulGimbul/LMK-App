package com.abdl.mylmk_app.data.source.remote.model

import com.abdl.mylmk_app.data.source.local.entity.UserEntity

data class AuthResponse(
    val status: Int?,
    val message: String?,
    val user: UserEntity
)