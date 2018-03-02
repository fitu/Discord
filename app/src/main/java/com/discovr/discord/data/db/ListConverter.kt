package com.discovr.discord.data.db

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {
    // TODO inject this?
    private var gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(data: List<String>?): String? {
        return gson.toJson(data)
    }
}
