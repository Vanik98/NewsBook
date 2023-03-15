@file:OptIn(ExperimentalSerializationApi::class)

package com.vanik.newsbook.domain

import android.util.Log
import com.vanik.newsbook.data.module.exeption.ResponseState
import com.vanik.newsbook.data.module.repository.Repository
import com.vanik.newsbook.data.proxy.model.ResultLocal
import com.vanik.newsbook.data.proxy.net.News
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class GetAllResultsUseCase(
    private val getNetResults: GetNetResultUseCase,
    private val getFavoriteResult: GetFavoriteResultUseCase
) {
    private var saveDbResult: List<ResultLocal>? = null
    private var showResult = arrayListOf<ResultLocal>()

    fun execute(page: Int) =
        combine(getNetResults.execute(page), getFavoriteResult.execute()) { netResults, dBResults ->
            val filterResultLocal: List<ResultLocal>
            if (page == 1) {
                filterResultLocal = FilterLogic.filterResults(dBResults, netResults, true)
                saveDbResult = dBResults
            } else {
                filterResultLocal = FilterLogic.filterResults(saveDbResult, netResults, false)
                showResult.addAll(filterResultLocal)
            }
            showResult.addAll(filterResultLocal)
            filterResultLocal
        }
}

class GetNetResultUseCase(private val repository: Repository) {
    private val responseState = MutableStateFlow<ResponseState>(ResponseState.START)
    fun execute(page: Int) =  flow {

        if(repository.getNetResults(page).isSuccessful){
            val newsJson = repository.getNetResults(page).body().toString()
            val json = Json { ignoreUnknownKeys = true }
            val news: News = json.decodeFromString(newsJson)
            emit(news.response?.results)
            responseState.emit(ResponseState.SUCCESS(news))
        }else{
            responseState.emit(ResponseState.FAILURE(repository.getNetResults(page).message()))
            Log.i("vanikTest","message ==== ${repository.getNetResults(page).message()}")
        }
    }
}

class GetFavoriteResultUseCase(private val repository: Repository) {
    fun execute() = repository.getDbResultsLocal()
}

class DeleteFavoriteResultUseCase(private val repository: Repository) {
    suspend fun execute(resultLocal: ResultLocal) = repository.deleteFavoriteResulLocal(resultLocal)
}

class SaveFavoriteResultUseCase(private val repository: Repository) {
    suspend fun execute(resultLocal: ResultLocal) = repository.saveFavoriteResultLocal(resultLocal)
}