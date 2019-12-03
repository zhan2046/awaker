package com.ruzhan.day.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ruzhan.day.db.converters.DayConverters
import com.ruzhan.day.db.dao.DayNewDao
import com.ruzhan.day.db.entity.DayNew

@TypeConverters(DayConverters::class)
@Database(entities = [DayNew::class], version = 1)
abstract class DayDatabase : RoomDatabase() {

    companion object {

        private const val DB_NAME = "day_new.db"
        @Volatile
        private var INSTANCE: DayDatabase? = null

        fun get(context: Context): DayDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        DayDatabase::class.java, DB_NAME)
                        .build()
    }

    abstract fun dayNewDao(): DayNewDao
}