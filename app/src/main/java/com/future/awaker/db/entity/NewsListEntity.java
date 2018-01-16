package com.future.awaker.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.future.awaker.data.News;
import com.future.awaker.db.converter.RoomDataConverter;

import java.util.List;


@Entity(tableName = "news_list_entity")
public class NewsListEntity {

    @NonNull
    @PrimaryKey
    public String id;
    @TypeConverters(RoomDataConverter.class)
    public List<News> newsList;

    public NewsListEntity(@NonNull String id, List<News> newsList) {
        this.id = id;
        this.newsList = newsList;
    }
}
