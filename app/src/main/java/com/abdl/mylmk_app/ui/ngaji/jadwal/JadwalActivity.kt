package com.abdl.mylmk_app.ui.ngaji.jadwal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdl.mylmk_app.data.repository.MainRepository
import com.abdl.mylmk_app.data.source.remote.RemoteDataSource
import com.abdl.mylmk_app.data.source.remote.services.ApiConfig
import com.abdl.mylmk_app.databinding.ActivityJadwalBinding
import com.abdl.mylmk_app.viewmodel.ViewModelFactory

class JadwalActivity : AppCompatActivity() {
    private lateinit var activityJadwalBinding: ActivityJadwalBinding
    private lateinit var viewModel: JadwalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityJadwalBinding = ActivityJadwalBinding.inflate(layoutInflater)
        setContentView(activityJadwalBinding.root)

        val factory =
            ViewModelFactory(MainRepository.getInstance(RemoteDataSource(ApiConfig.getService())))
        viewModel = ViewModelProvider(this, factory)[JadwalViewModel::class.java]

        val jadwal = viewModel.getJadwalUser()

        val jadwalAdapter = JadwalAdapter()
        jadwalAdapter.setJadwalList(jadwal)

        with(activityJadwalBinding.rvJadwal) {
            layoutManager = LinearLayoutManager(this@JadwalActivity)
            setHasFixedSize(true)
            adapter = jadwalAdapter
        }

        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Jadwal"
    }
}