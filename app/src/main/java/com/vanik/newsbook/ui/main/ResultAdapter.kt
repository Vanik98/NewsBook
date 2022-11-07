package com.vanik.newsbook.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vanik.newsbook.databinding.ItemResultBinding
import com.vanik.newsbook.proxy.net.Result


class ResultAdapter(
    private val results: List<Result>,
    val context: Context,
) : RecyclerView.Adapter<ResultAdapter.ResultHolder>() {

    private lateinit var binding: ItemResultBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultHolder {
        binding = ItemResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ResultHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultHolder, position: Int) {
        holder.bind(results[position])
    }

    override fun getItemCount() = results.size

    inner class ResultHolder(private val binding: ItemResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            result.fields?.thumbnail?.let { showResultImage(imageLink = it) }
        }

        private fun showResultImage(imageLink: String) {
            Glide.with(context)
                .load(imageLink)
                .into(binding.resultImage)
        }
    }

}