package com.future.awaker.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.future.awaker.data.SpecialDetail;

import io.reactivex.Flowable;


@Dao
public interface SpecialDetailDao {

    @Query("SELECT * FROM special_detail WHERE id = :id")
    Flowable<SpecialDetail> loadSpecialDetail(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSpecialDetail(SpecialDetail specialDetail);
}
