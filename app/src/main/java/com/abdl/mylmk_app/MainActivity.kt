package com.abdl.mylmk_app

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.abdl.mylmk_app.databinding.ActivityMainBinding
import com.abdl.mylmk_app.ui.auth.AuthViewModel
import com.abdl.mylmk_app.ui.auth.AuthViewModelFactory
import com.abdl.mylmk_app.ui.auth.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: AuthViewModel

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

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

            }
            R.id.logout -> {

                val onLogout = Intent(this, LoginActivity::class.java)
                onLogout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                onLogout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)

                viewModel.delete()
                onLogout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(onLogout)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}