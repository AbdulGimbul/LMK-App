package com.abdl.mylmk_app.data.source.remote

import androidx.lifecycle.MutableLiveData
import com.abdl.mylmk_app.data.source.remote.model.Guru
import com.abdl.mylmk_app.data.source.remote.model.GuruResponse
import com.abdl.mylmk_app.data.source.remote.services.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource constructor(private val apiService: ApiService) {
    //    val guruList = MutableLiveData<List<Guru>>()
    val errorMessage = MutableLiveData<String>()
//
//    fun getAllGuru(){
//        val response = apiService.getAllGuru()
//        response.enqueue(object : Callback<GuruResponse> {
//            override fun onResponse(
//                call: Call<GuruResponse>,
//                response: Response<GuruResponse>
//            ) {
//                guruList.postValue(response.body()?.guru)
//            }
//
//            override fun onFailure(call: Call<GuruResponse>, t: Throwable) {
//                errorMessage.postValue(t.message)
//            }
//        })
//    }

    val guruList = ArrayList<Guru>()
    fun loadGuru() {
        val response = apiService.getAllGuru()
        response.enqueue(object : Callback<GuruResponse> {
            override fun onResponse(call: Call<GuruResponse>, response: Response<GuruResponse>) {
                val result = response.body()?.guru
                if (result != null) {
                    guruList.addAll(result)
                }
            }

            override fun onFailure(call: Call<GuruResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

}