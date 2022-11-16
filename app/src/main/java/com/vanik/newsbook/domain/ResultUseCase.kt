@file:OptIn(ExperimentalSerializationApi::class)

package com.vanik.newsbook.domain

import android.util.Log
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
    fun execute(page: Int) = flow {
        coroutineScope {
            val netResults = async { getNetResults.execute(page) }
            if (page == 1) {
                Log.i("vanikTest", "ResultUseCase-> it is true $page")
                var dbResults = async { getFavoriteResult.execute() }
                val filterResultLocal =
                    FilterLogic.filterResults(dbResults.await(), netResults.await(), true)
                saveDbResult = dbResults.await()
                Log.i("vanikTest", "data= $saveDbResult")
                showResult.addAll(filterResultLocal)
                emit(filterResultLocal)
            } else {
                val filterResultLocal =
                    FilterLogic.filterResults(saveDbResult, netResults.await(), false)
                showResult.addAll(filterResultLocal)
                emit(filterResultLocal)
            }
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
    suspend fun execute(page: Int) = withContext(backgroundThread) {
        repository.getNetResults(page)
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





