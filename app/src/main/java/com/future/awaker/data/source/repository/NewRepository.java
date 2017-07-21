package com.future.awaker.data.source.repository;

import com.future.awaker.data.NewDetail;
import com.future.awaker.data.News;
import com.future.awaker.data.Special;
import com.future.awaker.data.SpecialDetail;
import com.future.awaker.data.source.local.ILocalNewDataSource;
import com.future.awaker.data.source.local.LocalNewDataSource;
import com.future.awaker.data.source.remote.INewDataSource;
import com.future.awaker.data.source.remote.RemoteNewDataSource;
import com.future.awaker.network.HttpResult;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public class NewRepository implements INewDataSource, ILocalNewDataSource {

    private static NewRepository INSTANCE;

    private INewDataSource remoteDataSource;
    private ILocalNewDataSource localNewDataSource;

    private NewRepository() {
        remoteDataSource = new RemoteNewDataSource();
        localNewDataSource = new LocalNewDataSource();
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
    public Flowable<RealmResults> getLocalNewList(HashMap<String, String> map) {
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

    @Override
    public void close() {
        localNewDataSource.close();
    }
}
