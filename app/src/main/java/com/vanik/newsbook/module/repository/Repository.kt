package com.vanik.newsbook.module.repository

import com.vanik.newsbook.module.net.NewsApiService
import com.vanik.newsbook.module.room.dao.ResultDao
import com.vanik.newsbook.proxy.net.News
import com.vanik.newsbook.proxy.net.Result
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
class Repository(
    private val apiService: NewsApiService,
    private val resultDao: ResultDao
) {
    fun getResults() = flow{
        val newsJson = apiService.getNews().body().toString()
        val json = Json { ignoreUnknownKeys = true }
        val news: News = json.decodeFromString(newsJson)
        emit(news.response?.results)
    }

    fun getFavoriteNews() =flow{ emit(resultDao.getAll())}


    suspend fun saveFavoriteNews(result: Result) {
        resultDao.insert(result)
    }

    suspend fun deleteFavoriteNews(result: Result) {
        resultDao.delete(result)
    }

}