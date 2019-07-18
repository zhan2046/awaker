package com.ruzhan.awaker.article.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.ruzhan.awaker.article.db.entity.UserInfoEntity

import io.reactivex.Flowable


@Dao
interface UserInfoDao {

    @Query("SELECT * FROM user_info_entity WHERE id = :id")
    fun loadUserInfoEntity(id: String): Flowable<UserInfoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserInfoEntity(userInfoEntity: UserInfoEntity)
}
