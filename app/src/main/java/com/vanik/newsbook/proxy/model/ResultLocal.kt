package com.vanik.newsbook.proxy.model

import com.vanik.newsbook.proxy.net.Result

data class ResultLocal(
    val result: Result,
    var isSave: Boolean
)