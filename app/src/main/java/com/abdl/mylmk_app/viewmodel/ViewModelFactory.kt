package com.abdl.mylmk_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abdl.mylmk_app.data.repository.MainRepository
import com.abdl.mylmk_app.ui.home.detail.DetailViewModel
import com.abdl.mylmk_app.ui.home.guru.GuruViewModel

class ViewModelFactory constructor(private val repository: MainRepository) :
    ViewModelProvider.NewInstanceFactory() {

//    companion object{
//        @Volatile
//        private var instance: ViewModelFactory? = null
//
//        fun getInstance(context: Context): ViewModelFactory =
//            instance ?: synchronized(this){
//                instance ?: ViewModelFactory(ApiConfig.getService()).apply { instance = this }
//            }
//    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(GuruViewModel::class.java) -> {
                return GuruViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                return DetailViewModel(repository) as T
            }
            else -> throw Throwable("Unknown ViewModel class:" + modelClass.name)
        }
    }
}