package com.abdl.mylmk_app.ui.home.pendaftaran

import android.annotation.TargetApi
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.abdl.mylmk_app.databinding.ActivityDaftarBinding
import com.abdl.mylmk_app.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DaftarActivity : AppCompatActivity() {
    var uploadMessage: ValueCallback<Array<Uri>>? = null
    private val FILECHOOSER_RESULTCODE = 1
    val REQUEST_SELECT_FILE = 100
    private var mUploadMessage: ValueCallback<Uri>? = null

    private lateinit var activityDaftarBinding: ActivityDaftarBinding
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDaftarBinding = ActivityDaftarBinding.inflate(layoutInflater)
        setContentView(activityDaftarBinding.root)

        activityDaftarBinding.webView
        activityDaftarBinding.webView.settings.setSupportMultipleWindows(true)

        activityDaftarBinding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }
        //WebClient allows you to handle
        //onPageFinished and override Url loading
        activityDaftarBinding.webView.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                window.setTitle(title)
            }

            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                if (message != null) {
                    Log.d("alert", message)
                }
                val dialogBuilder = AlertDialog.Builder(this@DaftarActivity)
                dialogBuilder.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("OK") { _, _ ->
                        if (result != null) {
                            result.confirm()
                        }
                    }

                val alert = dialogBuilder.create()
                alert.show()

                return true
            }

            // For 3.0+ Devices (Start)
            // onActivityResult attached before constructor
            protected fun openFileChooser(uploadMsg: ValueCallback<Uri>, acceptType: String) {
                mUploadMessage = uploadMsg
                val i = Intent(Intent.ACTION_GET_CONTENT)
                i.addCategory(Intent.CATEGORY_OPENABLE)
                i.type = "image/*"
                startActivityForResult(
                    Intent.createChooser(i, "File Chooser"),
                    FILECHOOSER_RESULTCODE
                )
            }

            // For Lollipop 5.0+ Devices
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onShowFileChooser(
                mWebView: WebView,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
                if (uploadMessage != null) {
                    uploadMessage!!.onReceiveValue(null)
                    uploadMessage = null
                }

                uploadMessage = filePathCallback

                val intent = fileChooserParams.createIntent()
                try {
                    startActivityForResult(intent, REQUEST_SELECT_FILE)
                } catch (e: Exception) {
                    uploadMessage = null
                    Toast.makeText(
                        this@DaftarActivity,
                        "Cannot Open File Chooser",
                        Toast.LENGTH_SHORT
                    )
                    return false
                }

                return true
            }

            //For Android 4.1 only
            protected fun openFileChooser(
                uploadMsg: ValueCallback<Uri>,
                acceptType: String,
                capture: String
            ) {
                mUploadMessage = uploadMsg
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                startActivityForResult(
                    Intent.createChooser(intent, "File Chooser"),
                    FILECHOOSER_RESULTCODE
                )
            }

            protected fun openFileChooser(uploadMsg: ValueCallback<Uri>) {
                mUploadMessage = uploadMsg
                val i = Intent(Intent.ACTION_GET_CONTENT)
                i.addCategory(Intent.CATEGORY_OPENABLE)
                i.type = "image/*"
                startActivityForResult(
                    Intent.createChooser(i, "File Chooser"),
                    FILECHOOSER_RESULTCODE
                )
            }

        }

        var idUser: Int

        //this will load the url of the website
        profileViewModel.user.observe(this, Observer {
            idUser = it.id_user!!.toInt()
            Log.d("DaftarActivity", "cek $idUser")
            activityDaftarBinding.webView.loadUrl("https://abdl.alterdev.id/daftar/index/$idUser")
        })

        //this will enable the javascript settings
        activityDaftarBinding.webView.settings.javaScriptEnabled = true
        activityDaftarBinding.webView.settings.domStorageEnabled = true

        activityDaftarBinding.webView.settings.allowContentAccess = true
        activityDaftarBinding.webView.settings.allowFileAccess = true
        //horizontal scroll
        activityDaftarBinding.webView.settings.useWideViewPort = true

        //if you want to enable zoom feature
        activityDaftarBinding.webView.settings.setSupportZoom(true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage != null) {
                    uploadMessage?.onReceiveValue(
                        WebChromeClient.FileChooserParams.parseResult(
                            resultCode,
                            data
                        )
                    )
                    uploadMessage = null
                }
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (mUploadMessage != null) {
                var result = data?.data
                mUploadMessage?.onReceiveValue(result)
                mUploadMessage = null
            }
        } else {
            Toast.makeText(
                this,
                "Failed to open file uploader, please check app permissions.",
                Toast.LENGTH_LONG
            ).show()
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
    //if you press back button this code will work
//    override fun onBackPressed() {
//        //if your website can go back it will go back
//        if (activityDaftarBinding.webView.canGoBack())
//            activityDaftarBinding.webView.goBack()
//        else
//            super.onBackPressed()
//    }
}