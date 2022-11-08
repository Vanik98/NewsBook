@file:OptIn(ExperimentalSerializationApi::class)

package com.vanik.newsbook.module

import com.vanik.newsbook.module.repository.Repository
import com.vanik.newsbook.proxy.net.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi

private val backgroundThread = Dispatchers.IO

class GetAllResultsUseCase(
    private val getNetResults: GetNetResultUseCase,
    private val getFavoriteResult: GetFavoriteResultUseCase
) {
    fun execute() = flow {
        coroutineScope {
            val channel = produce<List<Result>?> {
                getNetResults.execute().collect() {
                    send(it)
                }
                getFavoriteResult.execute().collect() {
                    send(it)
                }
            }
            val results = filterResults(channel.receive(), channel.receive())
            emit(results)
        }
    }

    private fun filterResults(
        firstResults: List<Result>?,
        secondResults: List<Result>?
    ): List<Result> {
        val finalResults = arrayListOf<Result>()
        if (firstResults != null && secondResults != null) {
            finalResults.addAll(firstResults)
            for (result1 in firstResults) {
                for (result2 in secondResults)
                    if (result2.id != result1.id) {
                        finalResults.add(result2)
                    }
            }
        } else if (firstResults == null && secondResults != null) {
            finalResults.addAll(secondResults)
        } else if (firstResults != null && secondResults == null) {
            finalResults.addAll(firstResults)
        }
        return finalResults
    }

}

class GetNetResultUseCase(private val repository: Repository) {
    fun execute() = repository.getResults().flowOn(backgroundThread)
}

class GetFavoriteResultUseCase(private val repository: Repository) {
    fun execute() = repository.getFavoriteNews().flowOn(backgroundThread)
}

class DeleteFavoriteResultUseCase(private val repository: Repository) {
    suspend fun execute(result: Result) = withContext(backgroundThread) {
        repository.deleteFavoriteNews(result)
    }
}

class SaveFavoriteResultUseCase(private val repository: Repository) {
    suspend fun execute(result: Result) = withContext(backgroundThread) {
        repository.saveFavoriteNews(result)
    }
}





