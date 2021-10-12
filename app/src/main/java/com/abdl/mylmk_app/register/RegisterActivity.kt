package com.abdl.mylmk_app.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abdl.mylmk_app.databinding.ActivityRegisterBinding
import com.abdl.mylmk_app.register.presenter.RegisterPresenter
import com.abdl.mylmk_app.register.presenter.RegisterView
import com.abdl.mylmk_app.ui.auth.LoginActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class RegisterActivity : AppCompatActivity(), RegisterView {
    private lateinit var presenter: RegisterPresenter
    private lateinit var activityRegisterBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)

        presenter = RegisterPresenter(this)

        with(activityRegisterBinding) {
            btnRegister.onClick {
                val nik = edtNik.text.toString()
                val nama = edtNama.text.toString()
                val username = edtUsername.text.toString()
                val jk = edtJk.text.toString()
                val alamat = edtAlamat.text.toString()
                val password = edtPass.text.toString()
                val repeatPassword = repeatPass.text.toString()


                presenter.register(nik, nama, username, jk, alamat, password, repeatPassword)

                supportActionBar?.elevation = 0f
            }
        }

    }

    override fun onSuccessRegister(msg: String?) {
        alert {
            title = "Information Register"
            message = "Success Register"
        }.show()
        startActivity<LoginActivity>()
        finish()
    }

    override fun onFailedRegister(msg: String?) {
        alert {
            title = "Information Register"
            message = msg.toString()
        }.show()
    }
}