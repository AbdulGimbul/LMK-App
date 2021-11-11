package com.abdl.mylmk_app.ui.home.guru

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdl.mylmk_app.data.repository.MainRepository
import com.abdl.mylmk_app.data.source.remote.RemoteDataSource
import com.abdl.mylmk_app.data.source.remote.services.ApiConfig
import com.abdl.mylmk_app.databinding.ActivityGuruBinding
import com.abdl.mylmk_app.viewmodel.ViewModelFactory

class GuruActivity : AppCompatActivity() {
    private lateinit var activityGuruBinding: ActivityGuruBinding
    private lateinit var viewModel: GuruViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityGuruBinding = ActivityGuruBinding.inflate(layoutInflater)
        setContentView(activityGuruBinding.root)

        val remoteDataSource = RemoteDataSource(ApiConfig.getService())

        val factory =
            ViewModelFactory(MainRepository.getInstance(remoteDataSource))
        viewModel = ViewModelProvider(this, factory)[GuruViewModel::class.java]

        val guru = viewModel.getAllGuru()

        val guruAdapter = GuruAdapter()
        guruAdapter.setGuruList(guru)

        with(activityGuruBinding.rvGuru) {
            layoutManager = LinearLayoutManager(this@GuruActivity)
            setHasFixedSize(true)
            adapter = guruAdapter
        }

        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Daftar Guru LMK"
    }
}