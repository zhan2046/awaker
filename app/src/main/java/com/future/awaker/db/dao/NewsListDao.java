package com.future.awaker.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.future.awaker.db.entity.NewsListEntity;

import io.reactivex.Flowable;

@Dao
public interface NewsListDao {

    @Query("SELECT * FROM news_list_entity WHERE id = :id")
    Flowable<NewsListEntity> loadNewsListEntity(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewsListEntity(NewsListEntity newsListEntity);
}
