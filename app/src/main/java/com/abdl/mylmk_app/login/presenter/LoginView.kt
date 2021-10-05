package com.abdl.mylmk_app.login.presenter

import com.abdl.mylmk_app.login.data.User

interface LoginView {
    fun onSuccessLogin(msg: String?, data: User?)
    fun onFailedLogin(msg: String?)
}