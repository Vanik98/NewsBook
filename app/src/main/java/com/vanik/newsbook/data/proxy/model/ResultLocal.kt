package com.vanik.newsbook.data.proxy.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vanik.newsbook.data.proxy.net.Result

@Entity
data class ResultLocal(
    @PrimaryKey(autoGenerate = true)
    val idResult: Int,
    @Embedded
    var result: Result,
    var isSave: Boolean
)