package com.future.day.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.future.day.db.entity.DayNewChild
import com.future.day.db.entity.Tags

class DayConverters {

    companion object {

        @TypeConverter
        @JvmStatic
        fun getTagList(json: String): ArrayList<Tags> {
            val listType = object : TypeToken<ArrayList<Tags>>() {}.type
            return Gson().fromJson(json, listType)
        }

        @TypeConverter
        @JvmStatic
        fun converterTagList(list: ArrayList<Tags>): String {
            return Gson().toJson(list)
        }

        @TypeConverter
        @JvmStatic
        fun getDayNewChildList(json: String): ArrayList<DayNewChild> {
            val listType = object : TypeToken<ArrayList<DayNewChild>>() {}.type
            return Gson().fromJson(json, listType)
        }

        @TypeConverter
        @JvmStatic
        fun converterDayNewChildList(list: ArrayList<DayNewChild>): String {
            return Gson().toJson(list)
        }
    }
}