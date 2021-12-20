package com.abdl.mylmk_app.ui.profile.presenter

import com.abdl.mylmk_app.data.source.remote.model.ResultStatus
import com.abdl.mylmk_app.data.source.remote.services.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdatePresenter(val updateView: UpdateView) {

    fun updateProfile(
        id: Int?,
        nama: String?,
        username: String?,
        alamat: String?,
        no_hp: String?
    ) {
        RetrofitInstance.api
            .updateProfile(id, nama, username, alamat, no_hp)
            .enqueue(object : Callback<ResultStatus> {
                override fun onFailure(call: Call<ResultStatus>, t: Throwable) {
                    updateView.onFailed(t.localizedMessage)
                }

                override fun onResponse(
                    call: Call<ResultStatus>,
                    response: Response<ResultStatus>
                ) {
                    if (response.body()?.status == 200) {
                        updateView.onSuccess(response.body()?.message)
                    } else {
                        updateView.onFailed(response.body()?.message)
                    }
                }
            })
    }
}