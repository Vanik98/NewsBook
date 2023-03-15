package com.vanik.newsbook.data.module.exeption

sealed class ResponseState() {
    object START : ResponseState()
    object LOADING : ResponseState()
    data class SUCCESS<T>(val data : T) : ResponseState()
    data class FAILURE(val message: String) : ResponseState()
}