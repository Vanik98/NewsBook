package com.vanik.newsbook.ui.main

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vanik.newsbook.R
import com.vanik.newsbook.databinding.ActivityMainBinding
import com.vanik.newsbook.data.proxy.model.ResultLocal
import com.vanik.newsbook.data.proxy.net.Result
import com.vanik.newsbook.ui.base.BaseActivity
import com.vanik.newsbook.ui.web.ResultWebActivity
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.internal.assertThreadHoldsLock
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalSerializationApi
class MainActivity : BaseActivity() {
    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ResultAdapter
    private var resultsLocal = arrayListOf<ResultLocal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showNews()
    }

    override fun setUpBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun setUpViews() {
        initializeAdapter()
    }

    @SuppressLint("NotifyDataSetChanged")
    @OptIn(ExperimentalSerializationApi::class)
    private fun showNews() {
        showDialog()
        viewModel.getResults().observe(this, Observer {
            resultsLocal.addAll(it)
            adapter.notifyDataSetChanged()
            closeDialog()
        })
    }

    private fun initializeAdapter() {
        val resultRecyclerView = binding.resultRecyclerview
        resultRecyclerView.layoutManager = LinearLayoutManager(this)
        resultRecyclerView.adapter = ResultAdapter(
            resultsLocal,
            this,
            { openMovieActivity(it) },
            {
                viewModel.saveResult(it)
                val element = it
                resultsLocal.remove(it)
                resultsLocal.add(0,element)
            },
            {
                viewModel.deleteResult(it)
                val r = it
                resultsLocal.remove(it)
                resultsLocal.add(r)
            }
        )
        adapter = resultRecyclerView.adapter as ResultAdapter
    }


    @SuppressLint("SuspiciousIndentation")
    private fun openMovieActivity(newsUrl: String) {
        val intent = Intent(this, ResultWebActivity::class.java)
        intent.putExtra("newsUrl", newsUrl)
        startActivity(intent)
    }

}