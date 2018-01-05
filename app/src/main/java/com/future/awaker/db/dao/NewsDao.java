package com.future.awaker.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.future.awaker.db.entity.NewsEntity;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT * FROM news_entity")
    LiveData<List<NewsEntity>> loadAllNewsEntitys();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<NewsEntity> newsEntities);
}
