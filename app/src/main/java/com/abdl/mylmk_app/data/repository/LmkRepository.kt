package com.abdl.mylmk_app.data.repository

import androidx.room.withTransaction
import com.abdl.mylmk_app.data.source.local.room.LmkDatabase
import com.abdl.mylmk_app.data.source.remote.networkBoundResource
import com.abdl.mylmk_app.data.source.remote.services.ApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

class LmkRepository @Inject constructor(
    private val api: ApiService,
    private val db: LmkDatabase
) {
    private val lmkDao = db.getLmkDao()

    fun getGuru() = networkBoundResource(
        query = {
            lmkDao.getAllGuru()
        },
        fetch = {
            delay(2000)
            api.getAllGuru().guru
        },
        saveFetchResult = { guru ->
            db.withTransaction {
                lmkDao.deleteAllGuru()
                lmkDao.insertGuru(guru)
            }
        }
    )
}