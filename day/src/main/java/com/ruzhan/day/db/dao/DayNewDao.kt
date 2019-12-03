package com.ruzhan.day.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruzhan.day.db.entity.DayNew
import io.reactivex.Flowable

@Dao
interface DayNewDao {

    @Query("SELECT * FROM day_new ORDER BY pubdate_timestamp DESC")
    fun loadDayNewList(): Flowable<List<DayNew>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDayNewList(dayNewList: List<DayNew>)
}