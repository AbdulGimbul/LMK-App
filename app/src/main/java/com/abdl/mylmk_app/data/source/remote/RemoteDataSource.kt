package com.abdl.mylmk_app.data.source.remote

import androidx.lifecycle.MutableLiveData
import com.abdl.mylmk_app.data.source.remote.model.GuruItem
import com.abdl.mylmk_app.data.source.remote.model.JadwalUserItem
import com.abdl.mylmk_app.data.source.remote.model.ProgramItem
import com.abdl.mylmk_app.data.source.remote.model.ProgramResponse
import com.abdl.mylmk_app.data.source.remote.services.ApiService
import com.abdl.mylmk_app.data.source.remote.services.SafeApiRequest
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) : SafeApiRequest() {

    val guruList = ArrayList<GuruItem>()
    var job: Job? = null
    val errorMessage = MutableLiveData<String>()
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun loadGuru() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = apiService.getAllGuru().guru
            withContext(Dispatchers.Main) {
                response.let { guruList.addAll(it) }
                loading.value = false
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

    fun loadJadwal(idUser: Int) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = apiService.getJadwalUser(idUser).jadwalUser
            withContext(Dispatchers.Main) {
                response.let { jadwalList.addAll(it) }
                loading.value = false
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

}