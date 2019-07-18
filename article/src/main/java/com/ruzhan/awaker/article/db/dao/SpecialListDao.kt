package com.ruzhan.awaker.article.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.ruzhan.awaker.article.db.entity.SpecialListEntity

import io.reactivex.Flowable


@Dao
interface SpecialListDao {

    @Query("SELECT * FROM special_list_entity WHERE id = :id")
    fun loadSpecialListEntity(id: String): Flowable<SpecialListEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(specialListEntity: SpecialListEntity)
}
