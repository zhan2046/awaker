package com.ruzhan.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface CommonDao {

    @Query("SELECT * FROM common_model WHERE id LIKE :id")
    fun getCommonModel(id: String): Flowable<CommonModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCommonModel(commonModel: CommonModel)
}