package com.abdl.mylmk_app.register.presenter

import com.abdl.mylmk_app.data.source.remote.services.RetrofitInstance
import com.abdl.mylmk_app.register.data.ResultRegister
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterPresenter(val registerView: RegisterView) {
    fun register(
        nama: String?,
        username: String?,
        jk: String?,
        alamat: String?,
        no_hp: String?,
        password: String?,
        repeatPassword: String?
    ) {
        RetrofitInstance.api
            .register(nama, username, jk, alamat, no_hp, password, repeatPassword)
            .enqueue(object : Callback<ResultRegister> {
                override fun onFailure(call: Call<ResultRegister>, t: Throwable) {
                    registerView.onFailedRegister(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ResultRegister>,
                    response: Response<ResultRegister>
                ) {
                    if (response.body()?.status == 201) {
                        registerView.onSuccessRegister(response.body()?.message)
                    } else {
                        registerView.onFailedRegister(response.body()?.message)
                    }
                }
            })
    }

}