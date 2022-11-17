package com.vanik.newsbook.presentation.ui.base

//noinspection SuspiciousImport

import android.R
import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Cache
import okhttp3.OkHttpClient


abstract class BaseActivity : AppCompatActivity() {

    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBaseViews()
        setUpViews()
    }

    abstract fun setUpViews()
    private fun setUpBaseViews() {
        initializeDialog()
    }

    private fun initializeDialog() {
        dialog = Dialog(this, R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen)
        dialog.setContentView(com.vanik.newsbook.R.layout.dialog_layout)
    }

    fun showDialog() {
        dialog.show()
    }

    fun closeDialog() {
        dialog.dismiss()
    }


    fun isInternetAvailable(): Boolean {
        var isConnected: Boolean = false // Initial Value
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

}