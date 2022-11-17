@file:OptIn(ExperimentalSerializationApi::class)

package com.vanik.newsbook.data.module.di

import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.vanik.newsbook.data.module.net.NewsApiService
import com.vanik.newsbook.data.module.repository.Repository
import com.vanik.newsbook.data.module.room.AppDatabase
import com.vanik.newsbook.domain.*
import com.vanik.newsbook.presentation.ui.main.MainViewModel
import com.vanik.newsbook.presentation.ui.web.ResultWebViewModel
import com.vanik.newsbook.presentation.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


val appModules by lazy {
    listOf(
        viewModelsModule,
        useCaseModule,
        repositoryModule,
        retrofitModule,
        roomModule
    )
}

private val viewModelsModule = module {
    viewModel {
        MainViewModel(get(), get(), get())
    }
    viewModel {
        ResultWebViewModel()
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
    single { Repository(Dispatchers.IO,get(), get()) }
}

private val roomModule = module {
    single { Room.databaseBuilder(get(), AppDatabase::class.java, Constants.NEWS_DATABASE_NAME).build() }
    single { get<AppDatabase>().ResultDao() }
}

private val retrofitModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(Json.asConverterFactory(Constants.CONVERT_FACTORY.toMediaType()))
            .build()
            .create(NewsApiService::class.java)
    }
    single { NewsApiService }
}

