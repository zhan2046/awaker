package com.ruzhan.awaker.article.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.ruzhan.awaker.article.db.converter.RoomDataConverter;

import java.util.List;

@Entity(tableName = "special_detail")
public class SpecialDetail {

    @NonNull
    @PrimaryKey
    public String id;
    public String title;
    public String content;
    public String cover;
    @TypeConverters(RoomDataConverter.class)
    public List<News> newslist;

    @Ignore
    public SpecialDetail() {

    }

    public SpecialDetail(@NonNull String id, String title, String content, String cover, List<News> newslist) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.cover = cover;
        this.newslist = newslist;
    }
}
