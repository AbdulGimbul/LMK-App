package com.abdl.mylmk_app.ui.home.guru

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdl.mylmk_app.databinding.ActivityGuruBinding
import com.abdl.mylmk_app.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuruActivity : AppCompatActivity() {
    private lateinit var activityGuruBinding: ActivityGuruBinding
    private val viewModel: GuruViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityGuruBinding = ActivityGuruBinding.inflate(layoutInflater)
        setContentView(activityGuruBinding.root)

        val guruAdapter = GuruAdapter()

        activityGuruBinding.apply {
            rvGuru.apply {
                adapter = guruAdapter
                layoutManager = LinearLayoutManager(this@GuruActivity)
            }

            viewModel.guru.observe(this@GuruActivity, { result ->
                guruAdapter.setGuruList(result.data)

                progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
            })
        }
    }
}