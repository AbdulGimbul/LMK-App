package com.abdl.mylmk_app.data.repository

import com.abdl.mylmk_app.data.source.local.LocalDataSource
import com.abdl.mylmk_app.data.source.local.entity.GuruEntity
import com.abdl.mylmk_app.data.source.local.entity.ProgramEntity
import com.abdl.mylmk_app.data.source.remote.RemoteDataSource
import javax.inject.Inject

class MainRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    LocalDataSource {

    companion object {
        @Volatile
        private var instance: MainRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource): MainRepository =
            instance ?: synchronized(this) {
                instance ?: MainRepository(remoteDataSource).apply { instance = this }
            }
    }

    override fun getAllGuru(): List<GuruEntity> {
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

    override fun getAllProgram(): List<ProgramEntity> {
//        remoteDataSource.loadProgram()
        val programResponse = remoteDataSource.programList
        val programList = ArrayList<ProgramEntity>()
        for (response in programResponse) {
            val program = ProgramEntity(
                response.idInfo,
                response.judul,
                response.deskripsi,
                response.gambar,
                response.type
            )
            programList.add(program)
        }
        return programList
    }

//    override fun getJadwalUser(): List<JadwalEntity> {
//        remoteDataSource.loadJadwal()
//        val jadwalResponse = remoteDataSource.jadwalList
//        val jadwalList = ArrayList<JadwalEntity>()
//        for (response in jadwalResponse) {
//            val jadwal = JadwalEntity(
//                response.hari,
//                response.namaMurid,
//                response.idJadwal,
//                response.jam,
//                response.alamatMurid,
//                response.namaGuru,
//                response.idMurid
//            )
//            jadwalList.add(jadwal)
//        }
//        return jadwalList
//    }

}