package com.ruzhan.awaker.article.db

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.support.annotation.VisibleForTesting

import com.ruzhan.awaker.article.db.converter.RoomDataConverter
import com.ruzhan.awaker.article.db.dao.BannerDao
import com.ruzhan.awaker.article.db.dao.CommentListDao
import com.ruzhan.awaker.article.db.dao.NewDetailDao
import com.ruzhan.awaker.article.db.dao.NewsDao
import com.ruzhan.awaker.article.db.dao.NewsListDao
import com.ruzhan.awaker.article.db.dao.SpecialDetailDao
import com.ruzhan.awaker.article.db.dao.SpecialListDao
import com.ruzhan.awaker.article.db.dao.UserInfoDao
import com.ruzhan.awaker.article.db.entity.CommentEntity
import com.ruzhan.awaker.article.db.entity.NewsEntity
import com.ruzhan.awaker.article.db.entity.SpecialListEntity
import com.ruzhan.awaker.article.db.entity.UserInfoEntity
import com.ruzhan.awaker.article.model.BannerItem
import com.ruzhan.awaker.article.model.NewDetail
import com.ruzhan.awaker.article.model.News
import com.ruzhan.awaker.article.model.SpecialDetail


@Database(entities = [News::class, SpecialListEntity::class, BannerItem::class, NewDetail::class, CommentEntity::class, NewsEntity::class, SpecialDetail::class, UserInfoEntity::class], version = 1, exportSchema = false)
@TypeConverters(RoomDataConverter::class)
abstract class AwakerArticleAppDatabase : RoomDatabase() {

    private val isDatabaseCreated = MutableLiveData<Boolean>()

    val databaseCreated: LiveData<Boolean>
        get() = isDatabaseCreated

    abstract fun newsDao(): NewsDao

    abstract fun specialListDao(): SpecialListDao

    abstract fun bannerDao(): BannerDao

    abstract fun newDetailDao(): NewDetailDao

    abstract fun commentListDao(): CommentListDao

    abstract fun newsListDao(): NewsListDao

    abstract fun specialDetailDao(): SpecialDetailDao

    abstract fun userInfoDao(): UserInfoDao

    private fun updateDatabaseCreated(context: Context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            isDatabaseCreated.postValue(true)
        }
    }

    companion object {

        @VisibleForTesting
        val DATABASE_NAME = "awaker-article-db"

        private var INSTANCE: AwakerArticleAppDatabase? = null


        operator fun get(context: Context): AwakerArticleAppDatabase {
            if (INSTANCE == null) {
                synchronized(AwakerArticleAppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context, AwakerArticleAppDatabase::class.java, DATABASE_NAME)
                                .build()
                        INSTANCE!!.updateDatabaseCreated(context)
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
