package com.future.day.db.entity

data class DayNewChild(
        var id: Int,
        var type: Int,
        var cat: String,
        var title: String,
        var cover_thumb: String,
        var cover_landscape: String,
        var cover_landscape_hd: String,
        var pubdate: String,
        var archive_timestamp: Long,
        var pubdate_timestamp: Long,
        var lastupdate_timestamp: Long,
        var caption_subtitle: String,
        var title_wechat_tml: String,
        var latitude: Double,
        var longitude: Double,
        var location: String,
        var content: String,
        var tags: ArrayList<Tags> = ArrayList(),
        var tagKey: String = ""
)