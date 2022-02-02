package com.abdl.mylmk_app.ui.home.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdl.mylmk_app.data.source.remote.model.GuruItem
import com.abdl.mylmk_app.data.source.remote.model.JadwalUserItem
import com.abdl.mylmk_app.databinding.ActivityDetailGuruBinding
import com.abdl.mylmk_app.vo.Resource
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailGuruActivity : AppCompatActivity() {

    private lateinit var activityDetailGuruBinding: ActivityDetailGuruBinding
    private val viewModel: DetailViewModel by viewModels()

    private var guru: GuruItem? = null
    private var myGuru: JadwalUserItem? = null

    companion object {
        const val EXTRA_GURU = "extra_guru"
        const val EXTRA_MY_GURU = "extra_my_guru"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailGuruBinding = ActivityDetailGuruBinding.inflate(layoutInflater)
        setContentView(activityDetailGuruBinding.root)

        guru = intent.getParcelableExtra(EXTRA_GURU)
        guru?.let { populateGuru(it) }

        myGuru = intent.getParcelableExtra(EXTRA_MY_GURU)
        myGuru?.let { populateMyGuru(it) }

        val idGuru = guru?.id_guru?.toInt() ?: myGuru?.idGuru?.toInt()

        val jadwalAdapter = JadwalGuruAdapter()

        activityDetailGuruBinding.apply {
            rvJadwalGuru.apply {
                adapter = jadwalAdapter
                layoutManager = LinearLayoutManager(this@DetailGuruActivity)
            }

            if (idGuru != null) {
                viewModel.getJadwalGuru(idGuru).observe(this@DetailGuruActivity, {
                    if (it != null) {
                        jadwalAdapter.setJadwalList(it.data)

                        activityDetailGuruBinding.progressBar.isVisible =
                            it is Resource.Loading && it.data.isNullOrEmpty()
                    } else {
                        Toast.makeText(
                            this@DetailGuruActivity,
                            "Jadwal guru masih kosong",
                            Toast.LENGTH_LONG
                        ).show()
                    }
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

    private fun populateMyGuru(guru: JadwalUserItem) {
        activityDetailGuruBinding.tvName.text = guru.namaGuru
        activityDetailGuruBinding.tvAddress.text = guru.alamatGuru

        Glide.with(this)
            .load(guru.avatarGuru)
            .apply(RequestOptions().override(350, 350))
            .into(activityDetailGuruBinding.imgAvatar)
    }
}