package com.abdl.mylmk_app.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.abdl.mylmk_app.MainActivity
import com.abdl.mylmk_app.R
import com.abdl.mylmk_app.databinding.ActivityLoginBinding
import com.abdl.mylmk_app.register.RegisterActivity
import com.abdl.mylmk_app.utils.ApiException
import com.abdl.mylmk_app.utils.NoInternetException
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        edtUsername = findViewById(R.id.username)
        edtPassword = findViewById(R.id.pass)

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this, MainActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

        binding.lupaPassword.setOnClickListener {
            Snackbar.make(binding.container, "Silahkan menghubungi admin!", Snackbar.LENGTH_LONG)
                .also { snackbar ->
                    snackbar.setAction("Ok") {
                        snackbar.dismiss()
                    }
                }.show()
        }

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser() {
        with(binding) {
            val username = username.text.toString().trim()
            val password = pass.text.toString().trim()

            var isEmptyFields = false

            if (username.isEmpty()) {
                isEmptyFields = true
                edtUsername.error = "Username tidak boleh kosong"
            }

            if (password.isEmpty()) {
                isEmptyFields = true
                edtPassword.error = "Password tidak boleh kosong"
            }

            if (!isEmptyFields) {

                lifecycleScope.launch {
                    try {
                        val authResponse = viewModel.userLogin(username, password)
                        if (authResponse.user != null) {
                            viewModel.saveLoggedInUser(authResponse.user)
                        } else {
                            Toast.makeText(this@LoginActivity, "Login gagal!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } catch (e: ApiException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this@LoginActivity,
                            "Login gagal! Silahkan cek kembali username dan password",
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (e: NoInternetException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this@LoginActivity,
                            "Periksa koneksi internet!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}