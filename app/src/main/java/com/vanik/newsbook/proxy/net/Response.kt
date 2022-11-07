package com.vanik.newsbook.proxy.net


@kotlinx.serialization.Serializable
data class Response (
    var status: String? = null,
    var userTier: String? = null,
    var total : Int? = null,
    var startIndex : Int? = null,
    var pageSize : Int? = null,
    var currentPage : Int? = null,
    var pages : Int? = null,
    var orderBy: String? = null,
    var results : List<Result>? = null
)
