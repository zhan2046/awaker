package com.future.awaker.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.future.awaker.data.BannerItem;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.News;
import com.future.awaker.data.SpecialDetail;
import com.future.awaker.db.converter.RoomDataConverter;
import com.future.awaker.db.dao.BannerDao;
import com.future.awaker.db.dao.CommentListDao;
import com.future.awaker.db.dao.NewDetailDao;
import com.future.awaker.db.dao.NewsDao;
import com.future.awaker.db.dao.NewsListDao;
import com.future.awaker.db.dao.SpecialDetailDao;
import com.future.awaker.db.dao.SpecialListDao;
import com.future.awaker.db.dao.UserInfoDao;
import com.future.awaker.db.entity.CommentEntity;
import com.future.awaker.db.entity.NewsEntity;
import com.future.awaker.db.entity.SpecialListEntity;
import com.future.awaker.db.entity.UserInfoEntity;

@Database(entities = {News.class, SpecialListEntity.class, BannerItem.class, NewDetail.class,
        CommentEntity.class, NewsEntity.class, SpecialDetail.class, UserInfoEntity.class},
        version = 1, exportSchema = false)
@TypeConverters(RoomDataConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    @VisibleForTesting
    public static final String DATABASE_NAME = "awaker-db";

    private static AppDatabase INSTANCE;

    public abstract NewsDao newsDao();

    public abstract SpecialListDao specialListDao();

    public abstract BannerDao bannerDao();

    public abstract NewDetailDao newDetailDao();

    public abstract CommentListDao commentListDao();

    public abstract NewsListDao newsListDao();

    public abstract SpecialDetailDao specialDetailDao();

    public abstract UserInfoDao userInfoDao();

    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();


    public static AppDatabase get(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                            .build();
                    INSTANCE.updateDatabaseCreated(context);
                }
            }
        }
        return INSTANCE;
    }

    private void updateDatabaseCreated(Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            isDatabaseCreated.postValue(true);
        }
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return isDatabaseCreated;
    }
}
