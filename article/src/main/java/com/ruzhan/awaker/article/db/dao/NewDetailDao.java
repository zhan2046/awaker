package com.ruzhan.awaker.article.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ruzhan.awaker.article.model.NewDetail;

import io.reactivex.Flowable;


@Dao
public interface NewDetailDao {

    @Query("SELECT * FROM new_detail WHERE id = :id")
    Flowable<NewDetail> loadNewsDetail(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewsDetail(NewDetail newDetail);
}
