package com.vanik.newsbook.module.room.dao

import androidx.room.*
import com.vanik.newsbook.proxy.net.Result


@Dao
interface ResultDao {
    @Query("SELECT * FROM result")
    fun getAll(): List<Result>

    @Insert
    suspend fun insert(result: Result)

    @Delete
    suspend fun delete(result: Result)
}