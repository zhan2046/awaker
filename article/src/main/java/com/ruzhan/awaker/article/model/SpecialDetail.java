package com.ruzhan.awaker.article.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.ruzhan.awaker.article.db.converter.RoomDataConverter;

import java.util.List;

/**
 * Created by ruzhan on 2017/7/15.
 */

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
