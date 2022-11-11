package com.vanik.newsbook.data.module.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vanik.newsbook.data.module.room.dao.ResultDao
import com.vanik.newsbook.data.proxy.model.ResultLocal
import com.vanik.newsbook.data.proxy.net.Result

@Database(entities = [ResultLocal::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ResultDao(): ResultDao
}
