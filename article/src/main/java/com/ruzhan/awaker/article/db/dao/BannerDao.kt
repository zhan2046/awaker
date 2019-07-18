package com.ruzhan.awaker.article.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.ruzhan.awaker.article.model.BannerItem

import io.reactivex.Flowable


@Dao
interface BannerDao {

    @Query("SELECT * FROM banner order by id desc")
    fun loadAllBanners(): Flowable<List<BannerItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllBanners(bannerItems: List<BannerItem>)
}
