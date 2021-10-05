package com.abdl.mylmk_app.data.repository

import com.abdl.mylmk_app.data.source.local.LocalDataSource
import com.abdl.mylmk_app.data.source.local.entity.GuruEntity
import com.abdl.mylmk_app.data.source.remote.RemoteDataSource

class MainRepository private constructor(private val remoteDataSource: RemoteDataSource) :
    LocalDataSource {
    companion object {
        @Volatile
        private var instance: MainRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): MainRepository =
            instance ?: synchronized(this) {
                instance ?: MainRepository(remoteDataSource).apply { instance = this }
            }
    }

    override fun getAllGuru(): ArrayList<GuruEntity> {
        remoteDataSource.loadGuru()
        val guruResponse = remoteDataSource.guruList
        val guruList = ArrayList<GuruEntity>()
        for (response in guruResponse) {
            val guru = GuruEntity(
                response.id_guru,
                response.username,
                response.nama,
                response.jk,
                response.tempat_lahir,
                response.tgl_lahir,
                response.alamat,
                response.avatar
            )
            guruList.add(guru)
        }
        return guruList
    }

    override fun getGuruById(guruId: String): GuruEntity {
        remoteDataSource.loadGuru()
        val guruResponse = remoteDataSource.guruList
        lateinit var guru: GuruEntity
        for (response in guruResponse) {
            if (response.id_guru == guruId) {
                guru = GuruEntity(
                    response.id_guru,
                    response.username,
                    response.nama,
                    response.jk,
                    response.tempat_lahir,
                    response.tgl_lahir,
                    response.alamat,
                    response.avatar
                )
            }
        }
        return guru
    }
}