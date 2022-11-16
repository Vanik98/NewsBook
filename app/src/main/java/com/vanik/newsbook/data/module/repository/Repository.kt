package com.vanik.newsbook.data.module.repository

import com.vanik.newsbook.data.module.net.NewsApiService
import com.vanik.newsbook.data.module.room.dao.ResultDao
import com.vanik.newsbook.data.proxy.model.ResultLocal
import com.vanik.newsbook.data.proxy.net.News
import com.vanik.newsbook.data.proxy.net.Result
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
class Repository(
    private val apiService: NewsApiService,
    private val resultDao: ResultDao
) {
    suspend fun getNetResults(page: Int): List<Result>? {
        val newsJson = apiService.getNews(page = page).body().toString()
        val json = Json { ignoreUnknownKeys = true }
        val news: News = json.decodeFromString(newsJson)
        return news.response?.results
    }

    suspend fun getDbResultsLocal() = resultDao.getAll()


    suspend fun saveFavoriteResultLocal(resultLocal: ResultLocal) {
        resultDao.insert(resultLocal)
    }

    suspend fun deleteFavoriteResulLocal(resultLocal: ResultLocal) {
        resultDao.delete(resultLocal)
    }

}