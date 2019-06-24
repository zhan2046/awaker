package com.ruzhan.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [CommonModel::class], version = 1)
abstract class CommonAppDatabase : RoomDatabase() {

    companion object {
        private const val DB_NAME = "common.db"
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