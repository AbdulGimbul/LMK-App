package com.abdl.mylmk_app.ui.ngaji.jadwal

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdl.mylmk_app.databinding.ActivityJadwalBinding
import com.abdl.mylmk_app.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JadwalActivity : AppCompatActivity() {
    private lateinit var activityJadwalBinding: ActivityJadwalBinding
    private val profileViewModel: ProfileViewModel by viewModels()
    private val viewModel: JadwalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityJadwalBinding = ActivityJadwalBinding.inflate(layoutInflater)
        setContentView(activityJadwalBinding.root)

        val jadwalAdapter = JadwalAdapter()

        activityJadwalBinding.apply {
            rvJadwal.apply {
                adapter = jadwalAdapter
                layoutManager = LinearLayoutManager(this@JadwalActivity)
                setHasFixedSize(true)
            }

            var idUser: Int

            profileViewModel.user.observe(this@JadwalActivity, Observer {
                idUser = it.id_user!!.toInt()
                Log.d("JadwalActivity", "cek $idUser")
                viewModel.getJadwal(idUser).observe(this@JadwalActivity, Observer { result ->
                    jadwalAdapter.setJadwalList(result.data)
                })
            })
        }

        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Jadwal"
    }
}