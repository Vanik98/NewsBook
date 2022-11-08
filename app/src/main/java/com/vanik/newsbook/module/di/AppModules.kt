@file:OptIn(ExperimentalSerializationApi::class)

package com.vanik.newsbook.module.di

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.vanik.newsbook.module.*
import com.vanik.newsbook.module.net.NewsApiService
import com.vanik.newsbook.module.repository.Repository
import com.vanik.newsbook.module.room.AppDatabase
import com.vanik.newsbook.ui.main.MainViewModel
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModules by lazy {
    listOf(
        mainViewModel,
        useCaseModule,
        repositoryModule,
        retrofitModule,
        roomModule
    )
}

private val mainViewModel = module {
    viewModel {
        MainViewModel(get(), get(), get())
    }
}

private val useCaseModule = module {
    single { GetAllResultsUseCase(get(), get()) }
    single { GetNetResultUseCase(get()) }
    single { GetFavoriteResultUseCase(get()) }
    single { SaveFavoriteResultUseCase(get()) }
    single { DeleteFavoriteResultUseCase(get()) }
}

private val repositoryModule = module {
    single { Repository(get(), get()) }
}

private val roomModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "news_database"
        ).build()
    }
    single { get<AppDatabase>().ResultDao() }
}

private val retrofitModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://content.guardianapis.com/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(NewsApiService::class.java)
    }
    single { NewsApiService }
}

