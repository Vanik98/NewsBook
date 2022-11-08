package com.vanik.newsbook.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vanik.newsbook.module.DeleteFavoriteResultUseCase
import com.vanik.newsbook.module.GetAllResultsUseCase
import com.vanik.newsbook.module.SaveFavoriteResultUseCase
import com.vanik.newsbook.proxy.net.Result
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MainViewModel(
    private val getAllResultsUseCase: GetAllResultsUseCase,
    private val saveFavoriteResult: SaveFavoriteResultUseCase,
    private val deleteFavoriteResult: DeleteFavoriteResultUseCase,
) : ViewModel() {

    fun getResults() = getAllResultsUseCase.execute().asLiveData()

    fun saveResult(result: Result) = viewModelScope.launch { saveFavoriteResult.execute(result) }

    fun deleteResult(result: Result) = viewModelScope.launch { deleteFavoriteResult.execute(result) }


}