package com.ruzhan.awaker.article.db.converter

import android.arch.persistence.room.TypeConverter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ruzhan.awaker.article.model.Comment
import com.ruzhan.awaker.article.model.News
import com.ruzhan.awaker.article.model.Special

import java.util.Date


object RoomDataConverter {
    @TypeConverter
    @JvmStatic
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    @JvmStatic
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    @JvmStatic
    fun toSpecialList(json: String): List<Special> {
        return Gson().fromJson(json, object : TypeToken<List<Special>>() {

        }.type)
    }

    @TypeConverter
    @JvmStatic
    fun toSpecialListJson(specialList: List<Special>): String {
        return Gson().toJson(specialList)
    }

    @TypeConverter
    @JvmStatic
    fun toCommentList(json: String): List<Comment> {
        return Gson().fromJson(json, object : TypeToken<List<Comment>>() {

        }.type)
    }

    @TypeConverter
    @JvmStatic
    fun toCommentListJson(commentList: List<Comment>): String {
        return Gson().toJson(commentList)
    }

    @TypeConverter
    @JvmStatic
    fun toNewsList(json: String): List<News> {
        return Gson().fromJson(json, object : TypeToken<List<News>>() {

        }.type)
    }

    @TypeConverter
    @JvmStatic
    fun toNewsListJson(newsList: List<News>): String {
        return Gson().toJson(newsList)
    }
}
