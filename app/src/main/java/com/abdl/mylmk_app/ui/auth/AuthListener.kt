package com.abdl.mylmk_app.ui.auth

import com.abdl.mylmk_app.data.source.local.entity.UserEntity

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: UserEntity?)
    fun onFailure(message: String)
}