package com.ruzhan.awaker.article.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ruzhan.awaker.article.model.News;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news ORDER BY id DESC")
    Flowable<List<News>> loadNewsList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewsList(List<News> newsList);
}
