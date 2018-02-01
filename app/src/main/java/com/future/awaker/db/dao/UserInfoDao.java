package com.future.awaker.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.future.awaker.db.entity.UserInfoEntity;

import io.reactivex.Flowable;


@Dao
public interface UserInfoDao {

    @Query("SELECT * FROM user_info_entity WHERE id = :id")
    Flowable<UserInfoEntity> loadUserInfoEntity(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserInfoEntity(UserInfoEntity userInfoEntity);
}
