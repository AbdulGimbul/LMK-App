package com.abdl.mylmk_app.ui.home.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.abdl.mylmk_app.data.repository.MainRepository
import com.abdl.mylmk_app.data.source.local.entity.GuruEntity
import com.abdl.mylmk_app.data.source.remote.RemoteDataSource
import com.abdl.mylmk_app.data.source.remote.services.ApiConfig
import com.abdl.mylmk_app.databinding.ActivityDetailGuruBinding
import com.abdl.mylmk_app.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailGuruActivity : AppCompatActivity() {

    private lateinit var activityDetailGuruBinding: ActivityDetailGuruBinding
    private lateinit var viewModel: DetailViewModel

    companion object {
        const val EXTRA_GURU = "extra_guru"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailGuruBinding = ActivityDetailGuruBinding.inflate(layoutInflater)
        setContentView(activityDetailGuruBinding.root)

        val factory =
            ViewModelFactory(MainRepository.getInstance(RemoteDataSource(ApiConfig.getService())))
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]


        val extras = intent.extras
        if (extras != null) {
            val guruId = extras.getString(EXTRA_GURU)
            if (guruId != null) {
                viewModel.setSelectedGuru(guruId)
                populateGuru(viewModel.getGuru())
            }
        }

        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Detail Guru"
    }

    private fun populateGuru(guru: GuruEntity) {
        activityDetailGuruBinding.tvName.text = guru.nama
        activityDetailGuruBinding.tvAddress.text = guru.alamat

        Glide.with(this)
            .load(guru.imagePath)
            .apply(RequestOptions().override(350, 350))
            .into(activityDetailGuruBinding.imgAvatar)
    }
}