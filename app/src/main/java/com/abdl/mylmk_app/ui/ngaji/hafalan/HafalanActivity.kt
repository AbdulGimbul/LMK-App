package com.abdl.mylmk_app.ui.ngaji.hafalan

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.abdl.mylmk_app.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HafalanActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hafalan)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}

const val ADD_HAFALAN_RESULT_OK = Activity.RESULT_FIRST_USER
const val UPDATE_HAFALAN_RESULT_OK = Activity.RESULT_FIRST_USER + 1