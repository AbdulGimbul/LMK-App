package com.abdl.mylmk_app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abdl.mylmk_app.data.repository.UserRepository
import com.abdl.mylmk_app.ui.profile.ProfileViewModel

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                return AuthViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                return ProfileViewModel(repository) as T
            }
            else -> throw Throwable("Unknown ViewModel class:" + modelClass.name)
        }
    }
}