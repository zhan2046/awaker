package com.ruzhan.awaker.article.db.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ruzhan.awaker.article.db.entity.NewsEntity;

import io.reactivex.Flowable;

@Dao
public interface NewsListDao {

    @Query("SELECT * FROM news_entity WHERE id = :id")
    Flowable<NewsEntity> loadNewsEntity(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewsEntity(NewsEntity newsEntity);
}
