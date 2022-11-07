package com.vanik.newsbook.module.repository

import com.vanik.newsbook.module.net.NewsApiHelper
import com.vanik.newsbook.module.net.RetrofitInstance
import com.vanik.newsbook.proxy.net.News
import com.vanik.newsbook.proxy.net.Result
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
class Repository {
    private val apiHelper: NewsApiHelper = NewsApiHelper(RetrofitInstance.newsApiService)

    suspend fun getResults(): List<Result>? {
        val newsJson = apiHelper.getNews().body().toString()
        val json = Json { ignoreUnknownKeys = true }
        val news: News = json.decodeFromString(newsJson)
        return news.response?.results
    }

    suspend fun saveFavoriteNews(result: Result){

    }

    suspend fun deleteFavoriteNews(result: Result){

    }

    suspend fun getFavoriteNews(){

    }
}