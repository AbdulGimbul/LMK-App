package com.abdl.mylmk_app.data.repository

import com.abdl.mylmk_app.data.source.local.entity.UserEntity
import com.abdl.mylmk_app.data.source.local.room.LmkDatabase
import com.abdl.mylmk_app.data.source.remote.model.AuthResponse
import com.abdl.mylmk_app.data.source.remote.services.ApiService
import com.abdl.mylmk_app.data.source.remote.services.SafeApiRequest
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(
    private val api: ApiService,
    private val db: LmkDatabase
) : SafeApiRequest() {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { api.userLogin(email, password) }
    }

    suspend fun saveUser(user: UserEntity) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()

    fun delete() {
        executorService.execute { db.getUserDao().deleteAll() }
    }

}