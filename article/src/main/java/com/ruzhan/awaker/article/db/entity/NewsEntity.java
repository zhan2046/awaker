package com.ruzhan.awaker.article.db.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.ruzhan.awaker.article.db.converter.RoomDataConverter;
import com.ruzhan.awaker.article.model.News;

import java.util.List;

@Entity(tableName = "news_entity")
public class NewsEntity {

    public static final String HOT_READ_NEWS_ALL = "hot_read_news_all";
    public static final String HOT_NEWS_ALL = "hot_news_all";

    @NonNull
    @PrimaryKey
    public String id;
    @TypeConverters(RoomDataConverter.class)
    public List<News> newsList;

    @Ignore
    public NewsEntity() {

    }

    public NewsEntity(@NonNull String id, List<News> newsList) {
        this.id = id;
        this.newsList = newsList;
    }
}
