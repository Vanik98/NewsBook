@file:OptIn(ExperimentalSerializationApi::class)

package com.vanik.newsbook.domain

import com.vanik.newsbook.domain.FilterLogic
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
    fun execute() = flow {
        coroutineScope {
            val netResults = async { getNetResults.execute() }
            val dbResults = async { getFavoriteResult.execute() }
            val filterResultLocal =  FilterLogic.filterResults(dbResults.await(), netResults.await())
            emit(filterResultLocal)
        }
    }
}
//        val dbResults = MutableStateFlow(listOf<Result>())
//        val netResults = MutableStateFlow(listOf<Result>())
//        coroutineScope {
//            getNetResults.execute().collect {
//                if (it != null) {
//                    netResults.value = it
//                }
//            }
//            getFavoriteResult.execute().collect {
//                dbResults.value = it
//            }
//            val filteredData: LiveData<List<Result>> =
//                combine(dbResults, netResults) { dbResults, netResult ->
//                    filterResults(dbResults, netResult)
//                }.asLiveData()
//            emit(filteredData.value)
//        }




class GetNetResultUseCase(private val repository: Repository) {
    suspend fun execute() = withContext(backgroundThread) {
        repository.getNetResults()
    }
}

class GetFavoriteResultUseCase(private val repository: Repository) {
    suspend fun execute() = withContext(backgroundThread) {
        repository.getDbResultsLocal()
    }
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





