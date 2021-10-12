package com.abdl.mylmk_app.ui.auth

import androidx.lifecycle.ViewModel
import com.abdl.mylmk_app.data.repository.UserRepository
import com.abdl.mylmk_app.data.source.local.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthViewModel(
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