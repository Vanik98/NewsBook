package com.vanik.newsbook.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vanik.newsbook.module.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MainViewModel : ViewModel() {
    private val repository = Repository()

     fun getNewsResults() = liveData(Dispatchers.IO) { emit(repository.getResults()) }
}