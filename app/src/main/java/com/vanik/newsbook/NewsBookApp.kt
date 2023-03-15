package com.vanik.newsbook

import android.app.Application
import com.vanik.newsbook.data.module.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class NewsBookApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@NewsBookApp)
            modules(appModules)
        }
    }

}