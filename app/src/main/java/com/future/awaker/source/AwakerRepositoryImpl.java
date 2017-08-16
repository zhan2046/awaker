package com.future.awaker.source;

import com.future.awaker.data.BannerItem;
import com.future.awaker.data.Comment;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.News;
import com.future.awaker.data.Special;
import com.future.awaker.data.SpecialDetail;
import com.future.awaker.network.HttpResult;
import com.future.awaker.source.local.ILocalDataSource;
import com.future.awaker.source.remote.IRemoteDataSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public final class AwakerRepositoryImpl implements IAwakerRepository {

    private IRemoteDataSource remoteDataSource;
    private ILocalDataSource localNewDataSource;

    public AwakerRepositoryImpl(IRemoteDataSource remoteDataSource,
                                ILocalDataSource localNewDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localNewDataSource = localNewDataSource;
    }

    ///////////////////////
    // remote
    ///////////////////////


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
    public Flowable<HttpResult<Object>> sendNewsComment(String token, String newId, String content, String open_id, String pid) {
        return remoteDataSource.sendNewsComment(token, newId, content, open_id, pid);
    }

    ///////////////////////
    // local
    ///////////////////////

    @Override
    public Flowable<RealmResults> getLocalRealm(Class clazz, HashMap<String, String> map) {
        return localNewDataSource.getLocalRealm(clazz, map);
    }

    @Override
    public void updateLocalRealm(RealmModel realmModel) {
        localNewDataSource.updateLocalRealm(realmModel);
    }

    @Override
    public void deleteLocalRealm(Class clazz, Map<String, String> map) {
        localNewDataSource.deleteLocalRealm(clazz, map);
    }

    @Override
    public void close() {
        localNewDataSource.close();
    }
}
