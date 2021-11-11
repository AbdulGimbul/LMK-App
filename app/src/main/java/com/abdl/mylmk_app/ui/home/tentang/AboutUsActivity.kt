package com.abdl.mylmk_app.ui.home.tentang

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abdl.mylmk_app.R

class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        supportActionBar?.elevation = 0f
        supportActionBar?.title = "Tentang Kami"
    }
}