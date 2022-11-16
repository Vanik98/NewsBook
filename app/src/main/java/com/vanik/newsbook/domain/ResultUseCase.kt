@file:OptIn(ExperimentalSerializationApi::class)

package com.vanik.newsbook.domain

import com.vanik.newsbook.data.module.repository.Repository
import com.vanik.newsbook.data.proxy.model.ResultLocal
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.ExperimentalSerializationApi

private val backgroundThread = Dispatchers.IO

class GetAllResultsUseCase(
    private val getNetResults: GetNetResultUseCase,
    private val getFavoriteResult: GetFavoriteResultUseCase
) {
    private var saveDbResult: List<ResultLocal>? = null
    private var showResult = arrayListOf<ResultLocal>()

    fun execute(page: Int) =
        combine(getNetResults.execute(page), getFavoriteResult.execute()) { netResults, dBResults ->
            if (page == 1) {
                val filterResultLocal = FilterLogic.filterResults(dBResults, netResults, true)
                saveDbResult = dBResults
                showResult.addAll(filterResultLocal)
                filterResultLocal
            } else {
                val filterResultLocal = FilterLogic.filterResults(saveDbResult, netResults, false)
                showResult.addAll(filterResultLocal)
                filterResultLocal
            }
        }
}

class GetNetResultUseCase(private val repository: Repository) {
    fun execute(page: Int) = repository.getNetResults(page).flowOn(backgroundThread)
}

class GetFavoriteResultUseCase(private val repository: Repository) {
    fun execute() = repository.getDbResultsLocal().flowOn(backgroundThread)
}

class DeleteFavoriteResultUseCase(private val repository: Repository) {
    suspend fun execute(resultLocal: ResultLocal) = withContext(backgroundThread) {
        repository.deleteFavoriteResulLocal(resultLocal)
    }
}

class SaveFavoriteResultUseCase(private val repository: Repository) {
    suspend fun execute(resultLocal: ResultLocal) = withContext(backgroundThread) {
        repository.saveFavoriteResultLocal(resultLocal)
    }
}