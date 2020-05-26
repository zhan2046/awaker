package com.future.movie.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.future.movie.db.entity.IntroduceItem
import com.future.movie.db.entity.VideoItem

class MovieConverters {

    companion object {

        @TypeConverter
        @JvmStatic
        fun getIntroduceItemList(json: String): ArrayList<IntroduceItem> {
            val listType = object : TypeToken<ArrayList<IntroduceItem>>() {}.type
            return Gson().fromJson(json, listType)
        }

        @TypeConverter
        @JvmStatic
        fun converterIntroduceItemList(list: ArrayList<IntroduceItem>): String {
            return Gson().toJson(list)
        }

        @TypeConverter
        @JvmStatic
        fun getVideoItemList(json: String): ArrayList<VideoItem> {
            val listType = object : TypeToken<ArrayList<VideoItem>>() {}.type
            return Gson().fromJson(json, listType)
        }

        @TypeConverter
        @JvmStatic
        fun converterVideoItemList(list: ArrayList<VideoItem>): String {
            return Gson().toJson(list)
        }
    }
}