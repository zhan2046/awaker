package com.future.awaker.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.future.awaker.db.entity.SpecialListEntity;

import io.reactivex.Flowable;


@Dao
public interface SpecialListDao {

    @Query("SELECT * FROM special_list_entity WHERE id = :id")
    Flowable<SpecialListEntity> loadSpecialListEntity(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(SpecialListEntity specialListEntity);
}
