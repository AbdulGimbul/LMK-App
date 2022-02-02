package com.abdl.mylmk_app.ui.home.program

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdl.mylmk_app.databinding.ActivityProgramBinding
import com.abdl.mylmk_app.vo.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProgramActivity : AppCompatActivity() {
    private lateinit var activityProgramBinding: ActivityProgramBinding
    private val viewModel: ProgramViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityProgramBinding = ActivityProgramBinding.inflate(layoutInflater)
        setContentView(activityProgramBinding.root)

        val programAdapter = ProgramAdapter()

        with(activityProgramBinding.rvProgram) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = programAdapter

            supportActionBar?.elevation = 0f
            supportActionBar?.title = "Program LMK"
        }

        viewModel.program.observe(this, { result ->
            programAdapter.setProgramList(result.data)

            activityProgramBinding.progressBar.isVisible =
                result is Resource.Loading && result.data.isNullOrEmpty()
        })
    }
}