package com.vanik.newsbook.module.net

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@ExperimentalSerializationApi
object RetrofitInstance {
    private const val baseUrl = "https://content.guardianapis.com/"
    private val contentType = "application/json".toMediaType()
    private val convertFactory = Json.asConverterFactory(contentType)

    private fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(convertFactory)
            .build()
    }

    val newsApiService: NewsApiService = getInstance().create(NewsApiService::class.java)
}