package com.abdl.mylmk_app.register.presenter

interface RegisterView {
    fun onSuccessRegister(msg: String?)
    fun onFailedRegister(msg: String?)
}