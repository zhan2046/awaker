package com.future.awaker.data.source;

import com.future.awaker.data.News;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.Special;
import com.future.awaker.data.SpecialDetail;
import com.future.awaker.data.source.local.LocalNewDataSource;
import com.future.awaker.data.source.local.LocalNewDataSourceImpl;
import com.future.awaker.data.source.remote.NewDataSource;
import com.future.awaker.data.source.remote.NewRemoteDataSource;
import com.future.awaker.network.HttpResult;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public class NewRepository implements NewDataSource, LocalNewDataSource {

    private static NewRepository INSTANCE;

    private NewDataSource remoteDataSource;
    private LocalNewDataSource localNewDataSource;

    private NewRepository() {
        remoteDataSource = new NewRemoteDataSource();
        localNewDataSource = new LocalNewDataSourceImpl();
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
    public <T extends RealmModel> Flowable<RealmResults<T>> getLocalNewList(HashMap<String, String> map) {
        return localNewDataSource.getLocalNewList(map);
    }

    @Override
    public void deleteLocalNewList(String newId) {
        localNewDataSource.deleteLocalNewList(newId);
    }

    @Override
    public void updateLocalNewList(String newId, List<News> newsList) {
        localNewDataSource.updateLocalNewList(newId, newsList);
    }
}
