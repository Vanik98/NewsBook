package com.vanik.newsbook

import android.app.Application
import android.widget.Toast

abstract class BaseApplication : Application() {

    private lateinit var connectiveObserver: ConnectiveObserver

    override fun onCreate() {
        super.onCreate()
    }
}