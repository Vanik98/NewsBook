@file:OptIn(ExperimentalSerializationApi::class)

package com.vanik.newsbook.domain

import com.vanik.newsbook.data.module.repository.Repository
import com.vanik.newsbook.data.proxy.model.ResultLocal
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.ExperimentalSerializationApi

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
    fun execute(page: Int) = repository.getNetResults(page)
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