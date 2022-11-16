package com.vanik.newsbook.presentation.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vanik.newsbook.R
import com.vanik.newsbook.data.proxy.model.ResultLocal
import com.vanik.newsbook.databinding.ItemDialogBinding
import com.vanik.newsbook.databinding.ItemResultBinding


class ResultAdapter(
    private val resultLocal: List<ResultLocal>,
    val context: Context,
    val onClick: (newsUrl: String) -> Unit,
    val saveResult: (resultLocal: ResultLocal) -> Unit,
    val deleteResult: (resultLocal: ResultLocal) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var binding: ItemResultBinding
    private lateinit var bindingDialog: ItemDialogBinding
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_ITEM) {
            binding = ItemResultBinding.inflate(inflater, parent, false)
            ResultHolder(binding)
        } else {
            bindingDialog = ItemDialogBinding.inflate(inflater, parent, false)
            return DialogHolder(bindingDialog)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_ITEM) {
            holder as ResultHolder
            holder.bind(resultLocal[position])
        }
    }

    override fun getItemCount(): Int {
        return if (resultLocal.isNotEmpty())
            resultLocal.size + 1;
        else resultLocal.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == resultLocal.size) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    inner class DialogHolder(private val binding: ItemDialogBinding) :
        RecyclerView.ViewHolder(binding.root) {}


    inner class ResultHolder(private val binding: ItemResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(resultLocal: ResultLocal) {
            binding.resultFavorite = resultLocal
            resultLocal.result.fields?.thumbnail?.let { showResultImage(imageLink = it) }
            showFavoriteImage(resultLocal.isSave)
            binding.root.setOnClickListener { onClick.invoke(resultLocal.result.webUrl) }
            binding.resultFavoriteIcon.setOnClickListener {
                saveOrDelete(resultLocal)
            }
        }

        private fun showResultImage(imageLink: String) {
            Glide.with(context)
                .load(imageLink)
                .into(binding.resultImage)
        }

        @SuppressLint("NotifyDataSetChanged")
        private fun saveOrDelete(resultLocal: ResultLocal) {
            when (resultLocal.isSave) {
                true -> {
                    deleteResult.invoke(resultLocal)
                    resultLocal.isSave = false
                    notifyDataSetChanged()
                }
                false -> {
                    resultLocal.isSave = true
                    saveResult.invoke(resultLocal)
                    notifyDataSetChanged()
                }
            }
            showFavoriteImage(resultLocal.isSave)
        }

        private fun showFavoriteImage(isSave: Boolean) {
            val imageId = when (isSave) {
                true -> R.drawable.save
                false -> R.drawable.saven
            }
            binding.resultFavoriteIcon.setImageResource(imageId)
        }
    }

}