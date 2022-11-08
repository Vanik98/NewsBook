package com.vanik.newsbook.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vanik.newsbook.R
import com.vanik.newsbook.databinding.ItemResultBinding
import com.vanik.newsbook.proxy.model.ResultLocal
import com.vanik.newsbook.proxy.net.Result


class ResultAdapter(
    private val resultLocal: List<ResultLocal>,
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
        holder.bind(resultLocal[position])
    }

    override fun getItemCount() = resultLocal.size

    inner class ResultHolder(private val binding: ItemResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(resultLocal: ResultLocal) {
            binding.resultFavorite = resultLocal
            resultLocal.result.fields?.thumbnail?.let { showResultImage(imageLink = it) }
            binding.resultFavoriteIcon.setOnClickListener{
                saveOrDelete(resultLocal)
            }
        }

        private fun showResultImage(imageLink: String) {
            Glide.with(context)
                .load(imageLink)
                .into(binding.resultImage)
        }

        @SuppressLint("NotifyDataSetChanged")
        private fun saveOrDelete(result: ResultLocal) {
            when (result.isSave) {
                true -> {
                    result.isSave = false
                }
                false -> {
                    result.isSave = true
                }
            }
            showFavoriteImage(result.isSave)
        }

        private fun showFavoriteImage(isSave : Boolean){
            val imageId = when(isSave){
                true-> R.drawable.save
                false->R.drawable.saven
            }
            binding.resultFavoriteIcon.setImageResource(imageId)
        }
    }

}