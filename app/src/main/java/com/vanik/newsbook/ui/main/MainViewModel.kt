package com.vanik.newsbook.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.vanik.newsbook.module.*
import com.vanik.newsbook.module.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MainViewModel(
    private val getAllResultsUseCase: GetAllResultsUseCase,
    private val saveFavoriteResult: SaveFavoriteResultUseCase,
    private val deleteFavoriteResult: DeleteFavoriteResultUseCase,
) : ViewModel() {

    fun getResults() = getAllResultsUseCase.execute().asLiveData()
}