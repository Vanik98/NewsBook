@file:OptIn(ExperimentalSerializationApi::class)

package com.vanik.newsbook.module

import com.vanik.newsbook.module.repository.Repository
import com.vanik.newsbook.proxy.net.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi

private val backgroundThread = Dispatchers.IO

class GetResult(private val repository: Repository) {
    suspend fun execute() = repository.getResults()
}

class SaveFavoriteNews(private val repository: Repository) {
    suspend fun execute(result:Result) = withContext(backgroundThread){
        repository.saveFavoriteNews(result)
    }
}

class DeleteFavoriteNews(private val repository: Repository) {
    suspend fun execute(result:Result) = withContext(backgroundThread){
        repository.deleteFavoriteNews(result)
    }
}

class GetFavoriteNews(private val repository: Repository) {
    suspend fun execute() = withContext(backgroundThread){
        repository.getFavoriteNews()
    }
}

