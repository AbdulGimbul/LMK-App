package com.abdl.mylmk_app.data.source.remote

import androidx.lifecycle.MutableLiveData
import com.abdl.mylmk_app.data.source.remote.model.*
import com.abdl.mylmk_app.data.source.remote.services.ApiConfig
import com.abdl.mylmk_app.data.source.remote.services.ApiService
import com.abdl.mylmk_app.data.source.remote.services.SafeApiRequest
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class RemoteDataSource constructor(private val apiService: ApiService) : SafeApiRequest() {

    val api = ApiConfig.getService()

    val guruList = ArrayList<GuruItem>()
    var job: Job? = null
    val errorMessage = MutableLiveData<String>()
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun loadGuru() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = api.getAllGuru().awaitResponse()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.guru?.let { guruList.addAll(it) }
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
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

    val jadwalList = ArrayList<JadwalUserItem>()

    fun loadJadwal() {
        val response = apiService.getJadwalUser()
        response.enqueue(object : Callback<JadwalUserResponse> {
            override fun onResponse(
                call: Call<JadwalUserResponse>,
                response: Response<JadwalUserResponse>
            ) {
                val result = response.body()?.jadwalUser
                if (result != null) {
                    jadwalList.addAll(result)
                }
            }

            override fun onFailure(call: Call<JadwalUserResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

}