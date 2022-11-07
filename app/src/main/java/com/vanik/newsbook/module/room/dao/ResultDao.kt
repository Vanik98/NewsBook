//package com.vanik.newsbook.module.room.dao
//import androidx.room.*
//import com.vanik.newsbook.proxy.net.Result
//
//
//@Dao
//interface ResultDao {
//    @Query("SELECT * FROM result")
//    fun getAll(): List<Result>
//
//    @Query("SELECT * FROM Result WHERE id IN (:id)")
//    fun loadAllByIds(id: IntArray): List<Result>
//
//    @Insert
//    fun insert(result: Result)
//
//    @Update
//    fun update(result: Result?)
//
//    @Delete
//    fun delete(result: Result)
//}