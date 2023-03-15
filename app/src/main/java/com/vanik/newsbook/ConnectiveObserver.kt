package com.vanik.newsbook

import kotlinx.coroutines.flow.Flow

interface ConnectiveObserver {
    fun observe() : Flow<Status>

    enum class Status{
        AVAILABLE,UNAVAILABLE,LOSING,LOST
    }


}