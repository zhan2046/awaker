package com.ruzhan.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CommonModel::class], version = 1)
abstract class CommonAppDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME = "awakercommon.db"
        @Volatile
        private var INSTANCE: CommonAppDatabase? = null

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(CommonAppDatabase::class) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
                CommonAppDatabase::class.java, DB_NAME)
                .build()
    }

    abstract fun commonDao(): CommonDao
}