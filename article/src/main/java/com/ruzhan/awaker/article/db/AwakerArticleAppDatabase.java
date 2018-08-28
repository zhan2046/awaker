package com.ruzhan.awaker.article.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.ruzhan.awaker.article.db.converter.RoomDataConverter;
import com.ruzhan.awaker.article.db.dao.BannerDao;
import com.ruzhan.awaker.article.db.dao.CommentListDao;
import com.ruzhan.awaker.article.db.dao.NewDetailDao;
import com.ruzhan.awaker.article.db.dao.NewsDao;
import com.ruzhan.awaker.article.db.dao.NewsListDao;
import com.ruzhan.awaker.article.db.dao.SpecialDetailDao;
import com.ruzhan.awaker.article.db.dao.SpecialListDao;
import com.ruzhan.awaker.article.db.dao.UserInfoDao;
import com.ruzhan.awaker.article.db.entity.CommentEntity;
import com.ruzhan.awaker.article.db.entity.NewsEntity;
import com.ruzhan.awaker.article.db.entity.SpecialListEntity;
import com.ruzhan.awaker.article.db.entity.UserInfoEntity;
import com.ruzhan.awaker.article.model.BannerItem;
import com.ruzhan.awaker.article.model.NewDetail;
import com.ruzhan.awaker.article.model.News;
import com.ruzhan.awaker.article.model.SpecialDetail;


@Database(entities = {News.class, SpecialListEntity.class, BannerItem.class, NewDetail.class,
        CommentEntity.class, NewsEntity.class, SpecialDetail.class, UserInfoEntity.class},
        version = 1, exportSchema = false)
@TypeConverters(RoomDataConverter.class)
public abstract class AwakerArticleAppDatabase extends RoomDatabase {

    @VisibleForTesting
    public static final String DATABASE_NAME = "awaker-article-db";

    private static AwakerArticleAppDatabase INSTANCE;

    public abstract NewsDao newsDao();

    public abstract SpecialListDao specialListDao();

    public abstract BannerDao bannerDao();

    public abstract NewDetailDao newDetailDao();

    public abstract CommentListDao commentListDao();

    public abstract NewsListDao newsListDao();

    public abstract SpecialDetailDao specialDetailDao();

    public abstract UserInfoDao userInfoDao();

    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();


    public static AwakerArticleAppDatabase get(Context context) {
        if (INSTANCE == null) {
            synchronized (AwakerArticleAppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, AwakerArticleAppDatabase.class, DATABASE_NAME)
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
