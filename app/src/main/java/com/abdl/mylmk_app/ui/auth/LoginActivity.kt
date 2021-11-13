package com.abdl.mylmk_app.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.abdl.mylmk_app.MainActivity
import com.abdl.mylmk_app.data.source.local.entity.UserEntity
import com.abdl.mylmk_app.databinding.ActivityLoginBinding
import com.abdl.mylmk_app.register.RegisterActivity
import com.abdl.mylmk_app.utils.ApiException
import com.abdl.mylmk_app.utils.NoInternetException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), AuthListener {


    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

        with(binding) {
            btnLogin.onClick {
                loginUser()
            }
        }

        binding.register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser() {
        with(binding) {
            val username = username.text.toString().trim()
            val password = pass.text.toString().trim()

            lifecycleScope.launch {
                try {
                    val authResponse = viewModel.userLogin(username, password)
                    if (authResponse.user != null) {
                        viewModel.saveLoggedInUser(authResponse.user)
                    } else {
                        toast("Gagal Login")
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                } catch (e: NoInternetException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onStarted() {
        toast("Login Started")
    }

    override fun onSuccess(user: UserEntity?) {
        if (user != null) {
            toast("${user.nama} Berhasil Login")
        }
    }

    override fun onFailure(message: String) {
        toast(message)
    }
}