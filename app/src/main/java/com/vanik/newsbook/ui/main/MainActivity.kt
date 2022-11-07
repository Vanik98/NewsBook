package com.vanik.newsbook.ui.main

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vanik.newsbook.R
import com.vanik.newsbook.databinding.ActivityMainBinding
import com.vanik.newsbook.proxy.net.Result
import kotlinx.serialization.ExperimentalSerializationApi

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ResultAdapter
    private var results = mutableListOf<Result>()
    private lateinit var dialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initAdapter()
        initAndShowDialog()
        showNews()
    }

    @SuppressLint("NotifyDataSetChanged")
    @OptIn(ExperimentalSerializationApi::class)
    private fun showNews(){
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getNewsResults().observe(this, Observer {
            results.addAll(it!!)
            adapter.notifyDataSetChanged()
            dialog.dismiss()
        })
    }

    private fun initAdapter() {
        val resultRecyclerView = binding.resultRecyclerview
        resultRecyclerView.layoutManager = LinearLayoutManager(this)
        resultRecyclerView.adapter = ResultAdapter(results, this)
        adapter = resultRecyclerView.adapter as ResultAdapter
    }
    private fun initAndShowDialog() {
        dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen)
        dialog.setContentView(R.layout.dialog_layout)
        dialog.show()
    }

}