package com.vanik.newsbook.data.module.repository

import com.vanik.newsbook.data.module.net.NewsApiService
import com.vanik.newsbook.data.module.room.dao.ResultDao
import com.vanik.newsbook.data.proxy.model.ResultLocal
import com.vanik.newsbook.data.proxy.net.News
import com.vanik.newsbook.data.proxy.net.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
class Repository(
    private val ioDispatcher: CoroutineDispatcher,
    private val apiService: NewsApiService,
    private val resultDao: ResultDao
) {
    fun getNetResults(page: Int) = flow {
        val newsJson = apiService.getNews(page = page).body().toString()
        val json = Json { ignoreUnknownKeys = true }
        val news: News = json.decodeFromString(newsJson)
        emit(news.response?.results)
    }.flowOn(ioDispatcher)

    fun getDbResultsLocal() = flow { emit(resultDao.getAll()) }.flowOn(ioDispatcher)

    suspend fun saveFavoriteResultLocal(resultLocal: ResultLocal) = withContext(ioDispatcher) {
        resultDao.insert(resultLocal)
    }

    suspend fun deleteFavoriteResulLocal(resultLocal: ResultLocal) = withContext(ioDispatcher) {
        resultDao.delete(resultLocal)
    }

}