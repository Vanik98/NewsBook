package com.vanik.newsbook.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vanik.newsbook.data.proxy.model.ResultLocal
import com.vanik.newsbook.domain.DeleteFavoriteResultUseCase
import com.vanik.newsbook.domain.GetAllResultsUseCase
import com.vanik.newsbook.domain.SaveFavoriteResultUseCase
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class MainViewModel(
    private val getAllResultsUseCase: GetAllResultsUseCase,
    private val saveFavoriteResult: SaveFavoriteResultUseCase,
    private val deleteFavoriteResult: DeleteFavoriteResultUseCase,
) : ViewModel() {
    private var pageCount = 0

    fun getResults(): LiveData<List<ResultLocal>> {
        pageCount++
        return getAllResultsUseCase.execute(pageCount).asLiveData()
    }

    fun saveResult(resultLocal: ResultLocal) = viewModelScope.launch { saveFavoriteResult.execute(resultLocal) }

    fun deleteResult(resultLocal: ResultLocal) = viewModelScope.launch { deleteFavoriteResult.execute(resultLocal) }
}