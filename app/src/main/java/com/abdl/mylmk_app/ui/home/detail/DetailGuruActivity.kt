package com.abdl.mylmk_app.ui.home.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdl.mylmk_app.data.source.remote.model.GuruItem
import com.abdl.mylmk_app.databinding.ActivityDetailGuruBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailGuruActivity : AppCompatActivity() {

    private lateinit var activityDetailGuruBinding: ActivityDetailGuruBinding
    private val viewModel: DetailViewModel by viewModels()

    private var guru: GuruItem? = null

    companion object {
        const val EXTRA_GURU = "extra_guru"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailGuruBinding = ActivityDetailGuruBinding.inflate(layoutInflater)
        setContentView(activityDetailGuruBinding.root)

        guru = intent.getParcelableExtra(EXTRA_GURU)
        guru?.let { populateGuru(it) }

        val idGuru = guru?.id_guru?.toInt()

        val jadwalAdapter = JadwalGuruAdapter()

        activityDetailGuruBinding.apply {
            rvJadwalGuru.apply {
                adapter = jadwalAdapter
                layoutManager = LinearLayoutManager(this@DetailGuruActivity)
            }

            if (idGuru != null) {
                viewModel.getJadwalGuru(idGuru).observe(this@DetailGuruActivity, Observer {
                    jadwalAdapter.setJadwalList(it.data)
                })
            }

            supportActionBar?.elevation = 0f
            supportActionBar?.title = "Detail Guru"
        }
    }

    private fun populateGuru(guru: GuruItem) {
        activityDetailGuruBinding.tvName.text = guru.nama
        activityDetailGuruBinding.tvAddress.text = guru.alamat

        Glide.with(this)
            .load(guru.avatar)
            .apply(RequestOptions().override(350, 350))
            .into(activityDetailGuruBinding.imgAvatar)
    }
}