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

//    var onlineInterceptor = Interceptor { chain ->
//        val response = chain.proceed(chain.request())
//        val maxAge = 60 // read from cache for 60 seconds even if there is internet connection
//        response.newBuilder()
//            .header("Cache-Control", "public, max-age=$maxAge")
//            .removeHeader("Pragma")
//            .build()
//    }
//
//
//    var offlineInterceptor = Interceptor { chain ->
//        var request: Request = chain.request()
//        if (!isInternetAvailable(this)) {
//            val maxStale = 60 * 60 * 24 * 30 // Offline cache available for 30 days
//            request = request.newBuilder()
//                .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
//                .removeHeader("Pragma")
//                .build()
//        }
//        chain.proceed(request)
//    }
//
//    fun x() {
//        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB
//        val cache = Cache(this.cacheDir, cacheSize)
//        val okHttpClient: OkHttpClient =
//            OkHttpClient.Builder()
//                .addInterceptor(offlineInterceptor)
//                .addNetworkInterceptor(onlineInterceptor)
//                .cache(cache)
//                .build()
//    }
//

}