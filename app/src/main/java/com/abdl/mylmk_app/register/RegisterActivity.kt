package com.abdl.mylmk_app.register

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.abdl.mylmk_app.databinding.ActivityRegisterBinding
import com.abdl.mylmk_app.register.presenter.RegisterPresenter
import com.abdl.mylmk_app.register.presenter.RegisterView
import com.abdl.mylmk_app.ui.auth.LoginActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.sdk27.coroutines.onClick

class RegisterActivity : AppCompatActivity(), RegisterView {
    private lateinit var presenter: RegisterPresenter
    private lateinit var activityRegisterBinding: ActivityRegisterBinding

    lateinit var radioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)

        presenter = RegisterPresenter(this)

        with(activityRegisterBinding) {
            btnRegister.onClick {
                val nama = edtNama.text.toString()
                val username = edtUsername.text.toString()
                val alamat = edtAlamat.text.toString()
                val no_hp = edtNohp.text.toString()
                val password = edtPass.text.toString()
                val repeatPassword = repeatPass.text.toString()

                val selectedOption: Int = rgJk.checkedRadioButtonId
                radioButton = findViewById(selectedOption)
                val jk = radioButton.text.toString()

                var isEmptyFields = false

                if (nama.isEmpty()) {
                    isEmptyFields = true
                    edtNama.error = "Nama tidak boleh kosong"
                }

                if (alamat.isEmpty()) {
                    isEmptyFields = true
                    edtAlamat.error = "Alamat tidak boleh kosong"
                }

                if (no_hp.isEmpty()) {
                    isEmptyFields = true
                    edtNohp.error = "No HP tidak boleh kosong"
                }

                if (username.isEmpty()) {
                    isEmptyFields = true
                    edtUsername.error = "Username tidak boleh kosong"
                }

                if (password.isEmpty()) {
                    isEmptyFields = true
                    edtPass.error = "Password tidak boleh kosong"
                }

                if (repeatPassword != password) {
                    repeatPass.error = "Password harus sesuai"
                }

                if (!isEmptyFields) {
                    presenter.register(nama, username, jk, alamat, no_hp, password, repeatPassword)
                }

                supportActionBar?.elevation = 0f
            }
        }
    }

    override fun onSuccessRegister(msg: String?) {
        alert {
            title = "Information Register"
            message = msg.toString()
        }.show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onFailedRegister(msg: String?) {
        alert {
            title = "Information Register"
            message = "Registrasi gagal, isi data dengan benar"
        }.show()
    }
}