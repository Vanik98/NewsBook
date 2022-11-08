package com.vanik.newsbook.module.room

import androidx.room.TypeConverter
import com.vanik.newsbook.proxy.net.Fields
import com.vanik.newsbook.proxy.net.News
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DataConverter {
        @TypeConverter
        fun fromFields(str: String): Fields? {
            return Json.decodeFromString(str)
        }

        @TypeConverter
        fun stringFields(fields: Fields): String {
            return Json.encodeToString(fields)
        }

}