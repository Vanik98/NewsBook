package com.vanik.newsbook.presentation.ui.base

import android.R
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.vanik.newsbook.ConnectiveObserver
import com.vanik.newsbook.NetworkConnectivityObserver
import kotlinx.coroutines.launch

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var dialog: Dialog
    private lateinit var connectiveObserver: NetworkConnectivityObserver
    var status: ConnectiveObserver.Status = ConnectiveObserver.Status.UNAVAILABLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBaseViews()
        setUpViews()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            internetCallback()
        }
    }

    abstract fun setUpViews()
    private fun setUpBaseViews() {
        initializeDialog()
    }

    open fun isInternetConnected (state :ConnectiveObserver.Status){}

    @RequiresApi(Build.VERSION_CODES.N)
    private fun internetCallback() {
        connectiveObserver = NetworkConnectivityObserver(this)
        lifecycleScope.launch {
            connectiveObserver.observe().collect {
                status = it
                isInternetConnected(it)
                Toast.makeText(applicationContext, "internet connection is $it", Toast.LENGTH_SHORT).show()
            }
        }

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
}