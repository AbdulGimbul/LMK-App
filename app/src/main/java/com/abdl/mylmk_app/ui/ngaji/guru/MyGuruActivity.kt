package com.abdl.mylmk_app.ui.ngaji.guru

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdl.mylmk_app.databinding.ActivityMyGuruBinding
import com.abdl.mylmk_app.ui.ngaji.jadwal.JadwalViewModel
import com.abdl.mylmk_app.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyGuruActivity : AppCompatActivity() {
    private lateinit var activityMyGuruBinding: ActivityMyGuruBinding
    private val profileViewModel: ProfileViewModel by viewModels()
    private val viewModel: JadwalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMyGuruBinding = ActivityMyGuruBinding.inflate(layoutInflater)
        setContentView(activityMyGuruBinding.root)

        val myGuruAdapter = MyGuruAdapter()

        activityMyGuruBinding.apply {
            rvMyGuru.apply {
                adapter = myGuruAdapter
                layoutManager = LinearLayoutManager(this@MyGuruActivity)
                setHasFixedSize(true)
            }

            var idUser: Int

            profileViewModel.user.observe(this@MyGuruActivity, Observer {
                idUser = it.id_user!!.toInt()
                Log.d("MyGuruActivity", "cek $idUser")
                viewModel.getJadwal(idUser).observe(this@MyGuruActivity, Observer { result ->
                    myGuruAdapter.setJadwalList(result.data)
                })
            })
        }

        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Guru Saya"
    }
}