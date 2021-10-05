package com.abdl.mylmk_app.ui.home.guru

import androidx.lifecycle.ViewModel
import com.abdl.mylmk_app.data.repository.MainRepository
import com.abdl.mylmk_app.data.source.local.entity.GuruEntity

class GuruViewModel constructor(private val repository: MainRepository) : ViewModel() {

//    val guruList = MutableLiveData<List<Guru>>()
//    val errorMessage = MutableLiveData<String>()
//
//    fun getAllGuru(){
//        val response = repository.getAllGuru()
//        response.enqueue(object : Callback<GuruResponse>{
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

    fun getAllGuru(): List<GuruEntity> = repository.getAllGuru()
}