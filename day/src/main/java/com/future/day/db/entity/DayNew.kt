package com.future.day.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_new")
data class DayNew(
    @PrimaryKey
    var id: Int,
    @ColumnInfo(name = "type")
    var type: Int,
    @ColumnInfo(name = "cat")
    var cat: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "cover_thumb")
    var cover_thumb: String,
    @ColumnInfo(name = "cover_landscape")
    var cover_landscape: String,
    @ColumnInfo(name = "cover_landscape_hd")
    var cover_landscape_hd: String,
    @ColumnInfo(name = "pubdate")
    var pubdate: String,
    @ColumnInfo(name = "archive_timestamp")
    var archive_timestamp: Long,
    @ColumnInfo(name = "pubdate_timestamp")
    var pubdate_timestamp: Long,
    @ColumnInfo(name = "lastupdate_timestamp")
    var lastupdate_timestamp: Long,
    @ColumnInfo(name = "caption_subtitle")
    var caption_subtitle: String,
    @ColumnInfo(name = "title_wechat_tml")
    var title_wechat_tml: String,
    @ColumnInfo(name = "latitude")
    var latitude: Double,
    @ColumnInfo(name = "longitude")
    var longitude: Double,
    @ColumnInfo(name = "location")
    var location: String,
    @ColumnInfo(name = "content")
    var content: String,
    @ColumnInfo(name = "tags")
    var tags: ArrayList<Tags> = ArrayList(),
    @ColumnInfo(name = "album")
    var album: ArrayList<DayNewChild> = ArrayList(),
    @ColumnInfo(name = "tag_key")
    var tagKey: String = "",
    var viewType: Int = -1
)