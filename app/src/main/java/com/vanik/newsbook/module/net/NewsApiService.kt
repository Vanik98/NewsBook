package com.vanik.newsbook.module.net

import kotlinx.serialization.json.JsonObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    companion object {
        private const val SEARCH = "search"
        private const val API_KEY = "api-key"
        private const val API_KEY_VALUE = "e4ee9fd2-a708-473d-9eb9-63a33a818d5f"
        private const val PAGE_SIZE = "page-size"
        private const val PAGE_SIZE_VALUE = 100
        private const val PAGE = "page"
        private const val PAGE_VALUE = 1
        private const val SHOW_FIELDS = "show-fields"
        private const val SHOW_FIELDS_VALUE = "thumbnail"
        private const val AUTO_REFRESH_TIME: Long = 30000
    }

    @GET(SEARCH)
   suspend fun getNews(
        @Query(API_KEY) apiKey: String = API_KEY_VALUE,
        @Query(PAGE_SIZE) page_size: Int = PAGE_SIZE_VALUE,
        @Query(PAGE) page: Int = PAGE_VALUE,
        @Query(SHOW_FIELDS) show_fields: String = SHOW_FIELDS_VALUE
    ): Response<JsonObject>

}