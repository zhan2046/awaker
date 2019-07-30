package com.ruzhan.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable

@Dao
interface CommonDao {

    @Query("SELECT * FROM common_model WHERE id LIKE :id")
    fun getCommonModel(id: Int): Flowable<CommonModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCommonModel(commonModel: CommonModel)
}