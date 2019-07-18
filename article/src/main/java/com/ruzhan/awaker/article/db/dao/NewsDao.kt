package com.ruzhan.awaker.article.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.ruzhan.awaker.article.model.News

import io.reactivex.Flowable

@Dao
interface NewsDao {

    @Query("SELECT * FROM news ORDER BY id DESC")
    fun loadNewsList(): Flowable<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsList(newsList: List<News>)
}
