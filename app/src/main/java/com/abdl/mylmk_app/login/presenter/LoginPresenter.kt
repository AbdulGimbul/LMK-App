package com.abdl.mylmk_app.login.presenter

import android.util.Log
import com.abdl.mylmk_app.data.source.remote.services.ApiConfig
import com.abdl.mylmk_app.login.data.ResultLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(val loginView: LoginView) {
    fun login(username: String, password: String) {
        ApiConfig.getService()
            .login(username, password)
            .enqueue(object : Callback<ResultLogin> {
                override fun onFailure(call: Call<ResultLogin>, t: Throwable) {
                    Log.d("", "Failed")
                }

                override fun onResponse(
                    call: Call<ResultLogin>,
                    response: Response<ResultLogin>
                ) {
                    if (response.isSuccessful && response.body()?.status == 200) {
                        loginView.onSuccessLogin(response.body()?.message, response.body()?.user)
                    } else {
                        loginView.onFailedLogin(response.body()?.message)
                    }
                }
            })
    }
}