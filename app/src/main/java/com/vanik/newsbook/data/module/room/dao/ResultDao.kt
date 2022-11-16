package com.vanik.newsbook.data.module.room.dao

import androidx.room.*
import com.vanik.newsbook.data.proxy.model.ResultLocal


@Dao
interface ResultDao {
    @Query("SELECT * FROM ResultLocal")
    suspend fun getAll(): List<ResultLocal>?

    @Insert
    suspend fun insert(resultLocal: ResultLocal)

    @Delete
    suspend fun delete(resultLocal: ResultLocal)
}