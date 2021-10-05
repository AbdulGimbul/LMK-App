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
    private val TAG = "GuruActivity"
    private lateinit var activityGuruBinding: ActivityGuruBinding
    private lateinit var viewModel: GuruViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityGuruBinding = ActivityGuruBinding.inflate(layoutInflater)
        setContentView(activityGuruBinding.root)

        val factory =
            ViewModelFactory(MainRepository.getInstance(RemoteDataSource(ApiConfig.getService())))
        viewModel = ViewModelProvider(this, factory)[GuruViewModel::class.java]

        val guru = viewModel.getAllGuru()
        val guruAdapter = GuruAdapter()
        guruAdapter.setGuruList(guru)

        with(activityGuruBinding.rvGuru) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = guruAdapter
        }

//        viewModel.guruList.observe(this, Observer {
//            Log.d(TAG, "onCreate: $it")
//            adapter.setGuruList(it)
//        })
//
//        viewModel.errorMessage.observe(this, Observer {
//
//        })

    }
}