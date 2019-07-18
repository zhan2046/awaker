package com.ruzhan.awaker.article.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.ruzhan.awaker.article.model.NewDetail

import io.reactivex.Flowable


@Dao
interface NewDetailDao {

    @Query("SELECT * FROM new_detail WHERE id = :id")
    fun loadNewsDetail(id: String): Flowable<NewDetail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsDetail(newDetail: NewDetail)
}
