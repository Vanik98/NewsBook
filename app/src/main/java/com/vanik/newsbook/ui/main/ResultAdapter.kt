package com.vanik.newsbook.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        }

        private fun showResultImage(imageLink: String) {
            Glide.with(context)
                .load(imageLink)
                .into(binding.resultImage)
        }

        @SuppressLint("NotifyDataSetChanged")
        private fun saveOrDelete(result: ResultLocal){
            when(result.isSave){
                true->{
                    result.isSave = false
//                    deleteResultInterface.deleteResult(result)
                }
                false->{
                    result.isSave = true
//                    if(!insertResultInterface.insertResult(result)){
//                        result.isSave = false
//                        Collections.swap(results)
//                        notifyDataSetChanged()
                    }
                }
            }
//            showFavoriteImage(result.isSave)
        }

}