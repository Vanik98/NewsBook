package com.vanik.newsbook.presentation.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vanik.newsbook.ConnectiveObserver
import com.vanik.newsbook.R
import com.vanik.newsbook.data.proxy.model.ResultLocal
import com.vanik.newsbook.databinding.ActivityMainBinding
import com.vanik.newsbook.presentation.ui.base.BaseActivity
import com.vanik.newsbook.presentation.ui.web.ResultWebActivity
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalSerializationApi
class MainActivity : BaseActivity() {
    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ResultAdapter
    private var resultsLocal = arrayListOf<ResultLocal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(status  == ConnectiveObserver.Status.UNAVAILABLE){
            Toast.makeText(this,"no internet",Toast.LENGTH_SHORT).show()
        }
    }

    override fun setUpViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initializeAdapter()
    }

    override fun isInternetConnected(state: ConnectiveObserver.Status) {
        if(state == ConnectiveObserver.Status.AVAILABLE) {
            showDialog()
            showNews()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @OptIn(ExperimentalSerializationApi::class)
    private fun showNews() {
        viewModel.getResults().observe(this, Observer {
            resultsLocal.addAll(it)
            adapter.notifyDataSetChanged()
            closeDialog()
        })
    }

    private fun initializeAdapter() {
        val resultRecyclerView = binding.resultRecyclerview
        val layoutManager = LinearLayoutManager(this)
        resultRecyclerView.layoutManager = layoutManager
        resultRecyclerView.adapter = ResultAdapter(
            resultsLocal,
            this,
            { openMovieActivity(it) },
            {
                viewModel.saveResult(it)
                val element = it
                resultsLocal.remove(it)
                resultsLocal.add(0, element)
            },
            {
                viewModel.deleteResult(it)
                val r = it
                resultsLocal.remove(it)
                resultsLocal.add(r)
            }
        )
        adapter = resultRecyclerView.adapter as ResultAdapter
        scrollRecyclerView(resultRecyclerView, layoutManager)
    }

    private fun scrollRecyclerView(recyclerView: RecyclerView, layoutManager: LinearLayoutManager) {
        var loading = false
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> loading = false
                    RecyclerView.SCROLL_STATE_DRAGGING -> loading = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val pastVisibleItems: Int
                val visibleItemCount: Int
                val totalItemCount: Int
                if (status == ConnectiveObserver.Status.AVAILABLE) {
                    if (dy > 0) {
                        visibleItemCount = layoutManager.childCount
                        totalItemCount = layoutManager.itemCount
                        pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                        if (loading) {
                            if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                                loading = false
                                showNews()
                            }
                        }
                    }
                }
            }
        })
    }


    @SuppressLint("SuspiciousIndentation")
    private fun openMovieActivity(newsUrl: String) {
        val intent = Intent(this, ResultWebActivity::class.java)
        intent.putExtra("newsUrl", newsUrl)
        startActivity(intent)
    }

}