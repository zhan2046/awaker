package com.future.awaker.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.future.awaker.data.BannerItem;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface BannerDao {

    @Query("SELECT * FROM banner order by id desc")
    Flowable<List<BannerItem>> loadAllBanners();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllBanners(List<BannerItem> bannerItems);
}
