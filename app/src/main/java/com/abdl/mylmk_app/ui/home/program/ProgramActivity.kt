package com.abdl.mylmk_app.ui.home.program

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdl.mylmk_app.data.repository.MainRepository
import com.abdl.mylmk_app.data.source.remote.RemoteDataSource
import com.abdl.mylmk_app.data.source.remote.services.RetrofitInstance
import com.abdl.mylmk_app.databinding.ActivityProgramBinding
import com.abdl.mylmk_app.viewmodel.ViewModelFactory

class ProgramActivity : AppCompatActivity() {
    private lateinit var activityProgramBinding: ActivityProgramBinding
    private lateinit var viewModel: ProgramViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityProgramBinding = ActivityProgramBinding.inflate(layoutInflater)
        setContentView(activityProgramBinding.root)

        val factory =
            ViewModelFactory(MainRepository.getInstance(RemoteDataSource(RetrofitInstance.api)))
        viewModel = ViewModelProvider(this, factory)[ProgramViewModel::class.java]

        val program = viewModel.getAllProgram()

        val programAdapter = ProgramAdapter()
        programAdapter.setProgramList(program)

        with(activityProgramBinding.rvProgram) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = programAdapter

            supportActionBar?.elevation = 0f
            supportActionBar?.title = "Program LMK"
        }
    }
}