package com.abdl.mylmk_app.ui.auth

import androidx.lifecycle.ViewModel
import com.abdl.mylmk_app.data.repository.UserRepository
import com.abdl.mylmk_app.data.source.local.entity.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    fun getLoggedInUser() = repository.getUser()

    suspend fun userLogin(
        email: String,
        password: String
    ) = withContext(Dispatchers.IO) { repository.userLogin(email, password) }

    suspend fun saveLoggedInUser(user: UserEntity) = repository.saveUser(user)

    fun delete() {
        repository.delete()
    }
}