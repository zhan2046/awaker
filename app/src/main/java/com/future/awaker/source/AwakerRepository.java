package com.future.awaker.source;

import com.future.awaker.Application;
import com.future.awaker.data.BannerItem;
import com.future.awaker.data.Comment;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.News;
import com.future.awaker.data.Special;
import com.future.awaker.data.SpecialDetail;
import com.future.awaker.data.UserInfo;
import com.future.awaker.db.AppDatabase;
import com.future.awaker.db.entity.SpecialListEntity;
import com.future.awaker.network.AwakerClient;
import com.future.awaker.network.HttpResult;
import com.future.awaker.source.local.ILocalDataSource;
import com.future.awaker.source.local.LocalDataSourceImpl;
import com.future.awaker.source.remote.IRemoteDataSource;
import com.future.awaker.source.remote.RemoteDataSourceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by ruzhan
 */

public final class AwakerRepository implements ILocalDataSource, IRemoteDataSource {

    private static AwakerRepository INSTANCE;

    private ILocalDataSource localDataSource;
    private IRemoteDataSource remoteDataSource;

    private AppDatabase appDatabase;

    private AwakerRepository() {
        localDataSource = new LocalDataSourceImpl();
        remoteDataSource = new RemoteDataSourceImpl(AwakerClient.get());

        appDatabase = AppDatabase.get(Application.get());
    }

    public static AwakerRepository get() {
        if (INSTANCE == null) {
            synchronized (AwakerRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AwakerRepository();
                }
            }
        }
        return INSTANCE;
    }

    public Flowable<List<BannerItem>> loadAllBanners() {
        return appDatabase.bannerDao().loadAllBanners();
    }

    public void insertAllBanners(List<BannerItem> bannerItems) {
        appDatabase.bannerDao().insertAllBanners(bannerItems);
    }

    public Flowable<SpecialListEntity> loadSpecialListEntity(String id) {
        return appDatabase.specialListDao().loadSpecialListEntity(id);
    }

    public void insertAll(SpecialListEntity specialListEntity) {
        appDatabase.specialListDao().insertAll(specialListEntity);
    }

    public Flowable<List<News>> loadNewsList() {
        return appDatabase.newsDao().loadNewsList();
    }

    public void insertNewsList(List<News> newsList) {
        appDatabase.newsDao().insertNewsList(newsList);
    }

    @Override
    public Flowable<RealmResults> getLocalRealm(Class clazz, HashMap<String, String> map) {
        return localDataSource.getLocalRealm(clazz, map);
    }

    @Override
    public void updateLocalRealm(RealmModel realmModel) {
        localDataSource.updateLocalRealm(realmModel);
    }

    @Override
    public void deleteLocalRealm(Class clazz, Map<String, String> map) {
        localDataSource.deleteLocalRealm(clazz, map);
    }

    @Override
    public void clearAll() {
        localDataSource.clearAll();
    }

    @Override
    public void close() {
        localDataSource.close();
    }

    @Override
    public Flowable<HttpResult<List<BannerItem>>> getBanner(String token, String advType) {
        return remoteDataSource.getBanner(token, advType);
    }

    @Override
    public Flowable<HttpResult<List<News>>> getNewList(String token, int page, int id) {
        return remoteDataSource.getNewList(token, page, id);
    }

    @Override
    public Flowable<HttpResult<List<Special>>> getSpecialList(String token, int page, int cat) {
        return remoteDataSource.getSpecialList(token, page, cat);
    }

    @Override
    public Flowable<HttpResult<NewDetail>> getNewDetail(String token, String newId) {
        return remoteDataSource.getNewDetail(token, newId);
    }

    @Override
    public Flowable<HttpResult<SpecialDetail>> getSpecialDetail(String token, String id) {
        return remoteDataSource.getSpecialDetail(token, id);
    }

    @Override
    public Flowable<HttpResult<List<Comment>>> getUpNewsComments(String token, String newId) {
        return remoteDataSource.getUpNewsComments(token, newId);
    }

    @Override
    public Flowable<HttpResult<List<Comment>>> getNewsComments(String token, String newId,
                                                               int page) {
        return remoteDataSource.getNewsComments(token, newId, page);
    }

    @Override
    public Flowable<HttpResult<List<News>>> getHotviewNewsAll(String token, int page, int id) {
        return remoteDataSource.getHotviewNewsAll(token, page, id);
    }

    @Override
    public Flowable<HttpResult<List<News>>> getHotNewsAll(String token, int page, int id) {
        return remoteDataSource.getHotNewsAll(token, page, id);
    }

    @Override
    public Flowable<HttpResult<List<Comment>>> getHotComment(String token) {
        return remoteDataSource.getHotComment(token);
    }

    @Override
    public Flowable<HttpResult<Object>> sendNewsComment(String token, String newId, String content,
                                                        String open_id, String pid) {
        return remoteDataSource.sendNewsComment(token, newId, content, open_id, pid);
    }

    @Override
    public Flowable<HttpResult<Object>> register(String token, String email, String nickname,
                                                 String password) {
        return remoteDataSource.register(token, email, nickname, password);
    }

    @Override
    public Flowable<UserInfo> login(String token, String username, String password) {
        return remoteDataSource.login(token, username, password);
    }
}
