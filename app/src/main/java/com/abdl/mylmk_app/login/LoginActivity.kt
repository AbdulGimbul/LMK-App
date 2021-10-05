package com.abdl.mylmk_app.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abdl.mylmk_app.MainActivity
import com.abdl.mylmk_app.databinding.ActivityLoginBinding
import com.abdl.mylmk_app.login.data.User
import com.abdl.mylmk_app.login.presenter.LoginPresenter
import com.abdl.mylmk_app.login.presenter.LoginView
import com.abdl.mylmk_app.register.RegisterActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity(), LoginView {
    private lateinit var presenter: LoginPresenter
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = LoginPresenter(this)

        with(binding) {
            btnLogin.onClick {
                val username = username.text.toString()
                val password = pass.text.toString()
                presenter.login(username, password)
            }

            register.onClick {
                startActivity<RegisterActivity>()
            }
        }
    }

    override fun onSuccessLogin(msg: String?, data: User?) {
        alert {
            title = "Information"
            message = "Login Success"
        }.show()
        startActivity<MainActivity>()
        finish()
    }

    override fun onFailedLogin(msg: String?) {
        alert {
            title = "Information"
            message = "Login failed, silahkan cek username dan password"
        }.show()
    }
}