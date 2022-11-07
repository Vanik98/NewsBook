package com.vanik.newsbook.module.net

class NewsApiHelper(private val newsApiService: NewsApiService) {
    suspend fun getNews() = newsApiService.getNews()
}