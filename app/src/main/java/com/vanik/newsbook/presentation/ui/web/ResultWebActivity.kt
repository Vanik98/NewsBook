package com.vanik.newsbook.presentation.ui.web

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.vanik.newsbook.R
import com.vanik.newsbook.databinding.ActivityResultWebBinding
import com.vanik.newsbook.presentation.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResultWebActivity : BaseActivity() {
    private val viewModel: ResultWebViewModel by viewModel()
    private lateinit var binding: ActivityResultWebBinding
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showWebView(savedInstanceState)
    }

    override fun setUpViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_result_web)
        initializeWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initializeWebView() {
        webView = binding.resultWebView
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                showDialog()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                closeDialog()
            }
        }

    }

    private fun showWebView(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        } else {
            val str: String? = intent.getStringExtra("newsUrl")
            if (str != null) {
                webView.loadUrl(str)
            }
            webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK;
        }
    }
}