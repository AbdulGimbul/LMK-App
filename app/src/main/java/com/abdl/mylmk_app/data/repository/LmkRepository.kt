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

    fun getProgram() = networkBoundResource(
        query = {
            lmkDao.getAllProgram()
        },
        fetch = {
            delay(2000)
            api.getAllProgram().program
        },
        saveFetchResult = { program ->
            db.withTransaction {
                lmkDao.deleteAllProgram()
                lmkDao.insertProgram(program)
            }
        }
    )

    fun getJadwal(idUser: Int) = networkBoundResource(
        query = {
            lmkDao.getJadwal()
        },
        fetch = {
            delay(2000)
            api.getJadwalUser(idUser).jadwalUser
        },
        saveFetchResult = { jadwal ->
            db.withTransaction {
                lmkDao.deleteJadwal()
                lmkDao.insertJadwal(jadwal)
            }
        }
    )

    fun getJadwalGuru(idGuru: Int) = networkBoundResource(
        query = {
            lmkDao.getJadwalGuru()
        },
        fetch = {
            delay(2000)
            api.getJadwalGuru(idGuru).jadwalGuru
        },
        saveFetchResult = { jadwal ->
            db.withTransaction {
                lmkDao.deleteJadwalGuru()
                lmkDao.insertJadwalGuru(jadwal)
            }
        }
    )
}