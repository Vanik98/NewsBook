package com.vanik.newsbook.ui.base

import android.R
import android.app.Dialog
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

open abstract class BaseActivity : AppCompatActivity() {

    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpBaseViews()
        setUpBinding()
        setUpViews()
    }

    abstract fun setUpBinding()
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

}