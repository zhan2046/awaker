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
import com.future.awaker.db.converter.RoomDataConverter;
import com.future.awaker.db.dao.BannerDao;
import com.future.awaker.db.dao.NewsListDao;
import com.future.awaker.db.dao.SpecialListDao;
import com.future.awaker.db.entity.NewsListEntity;
import com.future.awaker.db.entity.SpecialListEntity;

@Database(entities = {NewsListEntity.class, SpecialListEntity.class, BannerItem.class},
        version = 1, exportSchema = false)
@TypeConverters(RoomDataConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    @VisibleForTesting
    public static final String DATABASE_NAME = "awaker-db";

    private static AppDatabase INSTANCE;

    public abstract NewsListDao newsDao();

    public abstract SpecialListDao specialListDao();

    public abstract BannerDao bannerDao();

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
