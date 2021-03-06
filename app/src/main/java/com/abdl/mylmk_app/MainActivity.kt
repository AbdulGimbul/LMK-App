package com.abdl.mylmk_app

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.abdl.mylmk_app.databinding.ActivityMainBinding
import com.abdl.mylmk_app.ui.AboutActivity
import com.abdl.mylmk_app.ui.auth.AuthViewModel
import com.abdl.mylmk_app.ui.auth.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_ngaji, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.info_app -> {
                startActivity(Intent(this, AboutActivity::class.java))
            }
            R.id.logout -> {
                val onLogout = Intent(this, LoginActivity::class.java)
                onLogout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                onLogout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

                viewModel.delete()
                onLogout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(onLogout)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}