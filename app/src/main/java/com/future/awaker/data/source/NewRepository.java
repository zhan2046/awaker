package com.future.awaker.data.source;

import com.future.awaker.data.BannerItem;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.News;
import com.future.awaker.data.Special;
import com.future.awaker.data.SpecialDetail;
import com.future.awaker.network.HttpResult;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public final class NewRepository {

    private static NewRepository INSTANCE;

    private RemoteNewDataSource remoteDataSource = new RemoteNewDataSource();
    private LocalNewDataSource localNewDataSource = new LocalNewDataSource();

    private NewRepository() {
    }

    public static NewRepository get() {
        if (INSTANCE == null) {
            synchronized (NewRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NewRepository();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


    ///////////////////////
    // remote
    ///////////////////////


    public Flowable<HttpResult<List<BannerItem>>> getBanner(String token, String advType) {
        return remoteDataSource.getBanner(token, advType);
    }

    public Flowable<HttpResult<List<News>>> getNewList(String token, int page, int id) {
        return remoteDataSource.getNewList(token, page, id);
    }

    public Flowable<HttpResult<List<Special>>> getSpecialList(String token, int page, int cat) {
        return remoteDataSource.getSpecialList(token, page, cat);
    }

    public Flowable<HttpResult<NewDetail>> getNewDetail(String token, String newId) {
        return remoteDataSource.getNewDetail(token, newId);
    }

    public Flowable<HttpResult<SpecialDetail>> getSpecialDetail(String token, String id) {
        return remoteDataSource.getSpecialDetail(token, id);
    }


    ///////////////////////
    // local
    ///////////////////////


    public Flowable<RealmResults> getLocalNewList(HashMap<String, String> map) {
        return localNewDataSource.getLocalNewList(map);
    }

    public Flowable<RealmResults> getLocalSpecialList(HashMap<String, String> map) {
        return localNewDataSource.getLocalSpecialList(map);
    }

    public Flowable<RealmResults> getLocalBanner(HashMap<String, String> map) {
        return localNewDataSource.getLocalBanner(map);
    }

    public void updateLocalNewList(String newId, List<News> newsList) {
        localNewDataSource.updateLocalNewList(newId, newsList);
    }

    public void updateLocalSpecialList(String cat, List<Special> specialList) {
        localNewDataSource.updateLocalSpecialList(cat, specialList);
    }

    public void updateLocalBanner(String id, List<BannerItem> bannerItemList) {
        localNewDataSource.updateLocalBanner(id, bannerItemList);
    }

    public void deleteLocalNewList(String newId) {
        localNewDataSource.deleteLocalNewList(newId);
    }

    public void deleteLocalSpecialList(String cat) {
        localNewDataSource.deleteLocalSpecialList(cat);
    }

    public void deleteLocalBanner(String id) {
        localNewDataSource.deleteLocalBanner(id);
    }

    public void close() {
        localNewDataSource.close();
    }
}
