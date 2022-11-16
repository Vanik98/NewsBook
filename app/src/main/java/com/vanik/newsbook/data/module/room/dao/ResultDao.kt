package com.vanik.newsbook.data.module.room.dao

import androidx.room.*
import com.vanik.newsbook.data.proxy.model.ResultLocal
import com.vanik.newsbook.data.proxy.net.Result


@Dao
interface ResultDao {
    @Query("SELECT * FROM ResultLocal")
    fun getAll(): List<ResultLocal>?

    @Insert
    suspend fun insert(resultLocal: ResultLocal)

    @Delete
    suspend fun delete(resultLocal: ResultLocal)
}