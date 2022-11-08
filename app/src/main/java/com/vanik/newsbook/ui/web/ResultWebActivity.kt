package com.vanik.newsbook.ui.web

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.vanik.newsbook.R
import com.vanik.newsbook.databinding.ActivityResultWebBinding
import com.vanik.newsbook.ui.base.BaseActivity

class ResultWebActivity : BaseActivity() {
    private lateinit var binding: ActivityResultWebBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result_web)
        showWebView(savedInstanceState)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun showWebView(savedInstanceState: Bundle?) {
        val webView = binding.resultWebView
        webView.settings.javaScriptEnabled = true;
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        }
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        } else {
            getStringExtra(this::class.java,"newsUrl")
            intent.getStringExtra("newsUrl")?.let { webView.loadUrl(it) };
            webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK;
        }
    }
}