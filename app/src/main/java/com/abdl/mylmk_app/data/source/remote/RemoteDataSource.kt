package com.abdl.mylmk_app.data.source.remote

import androidx.lifecycle.MutableLiveData
import com.abdl.mylmk_app.data.source.remote.model.GuruItem
import com.abdl.mylmk_app.data.source.remote.model.GuruResponse
import com.abdl.mylmk_app.data.source.remote.model.ProgramItem
import com.abdl.mylmk_app.data.source.remote.model.ProgramResponse
import com.abdl.mylmk_app.data.source.remote.services.ApiService
import com.abdl.mylmk_app.data.source.remote.services.SafeApiRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource constructor(private val apiService: ApiService) : SafeApiRequest() {
    val guruList = ArrayList<GuruItem>()
    val errorMessage = MutableLiveData<String>()

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

    val programList = ArrayList<ProgramItem>()

    fun loadProgram() {
        val response = apiService.getAllProgram()
        response.enqueue(object : Callback<ProgramResponse> {
            override fun onResponse(
                call: Call<ProgramResponse>,
                response: Response<ProgramResponse>
            ) {
                val result = response.body()?.program
                if (result != null) {
                    programList.addAll(result)
                }
            }

            override fun onFailure(call: Call<ProgramResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

}