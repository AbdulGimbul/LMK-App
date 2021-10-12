package com.abdl.mylmk_app.login.presenter

class LoginPresenter(val loginView: LoginView) {
//    fun login(username: String, password: String) {
//        ApiConfig.getService()
//            .login(username, password)
//            .enqueue(object : Callback<ResultLogin> {
//                override fun onFailure(call: Call<ResultLogin>, t: Throwable) {
//                    Log.d("", "Failed")
//                }
//
//                override fun onResponse(
//                    call: Call<ResultLogin>,
//                    response: Response<ResultLogin>
//                ) {
//                    if (response.isSuccessful && response.body()?.status == 200) {
//                        loginView.onSuccessLogin(response.body()?.message, response.body()?.user)
//                    } else {
//                        loginView.onFailedLogin(response.body()?.message)
//                    }
//                }
//            })
//    }
}