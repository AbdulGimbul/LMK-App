package com.abdl.mylmk_app.ui.home.pendaftaran

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.abdl.mylmk_app.databinding.ActivityDaftarBinding

class DaftarActivity : AppCompatActivity() {
    private lateinit var activityDaftarBinding: ActivityDaftarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDaftarBinding = ActivityDaftarBinding.inflate(layoutInflater)
        setContentView(activityDaftarBinding.root)

        //WebClient allows you to handle
        //onPageFinished and override Url loading
        activityDaftarBinding.webView.webViewClient = WebViewClient()

        //this will load the url of the website
        activityDaftarBinding.webView.loadUrl("https://abdl.alterdev.id/daftar")

        //this will enable the javascript settings
        activityDaftarBinding.webView.settings.javaScriptEnabled = true

        //horizontal scroll
        activityDaftarBinding.webView.settings.useWideViewPort = true

        //if you want to enable zoom feature
        activityDaftarBinding.webView.settings.setSupportZoom(true)
    }

    //if you press back button this code will work
    override fun onBackPressed() {
        //if your website can go back it will go back
        if (activityDaftarBinding.webView.canGoBack())
            activityDaftarBinding.webView.goBack()
        else
            super.onBackPressed()
    }
}