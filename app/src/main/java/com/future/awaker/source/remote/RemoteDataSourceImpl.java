package com.future.awaker.source.remote;

import com.future.awaker.data.BannerItem;
import com.future.awaker.data.Comment;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.News;
import com.future.awaker.data.Special;
import com.future.awaker.data.SpecialDetail;
import com.future.awaker.data.UserInfo;
import com.future.awaker.network.AwakerApi;
import com.future.awaker.network.HttpResult;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright ©2017 by ruzhan
 */

public class RemoteDataSourceImpl implements IRemoteDataSource {

    private AwakerApi awakerApi;

    public RemoteDataSourceImpl(AwakerApi awakerApi) {
        this.awakerApi = awakerApi;
    }

    @Override
    public Flowable<HttpResult<List<BannerItem>>> getBanner(String token, String advType) {
        return awakerApi.getBanner(token, advType)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<List<News>>> getNewList(String token, int page, int id) {
        return awakerApi.getNewList(token, page, id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<List<Special>>> getSpecialList(String token, int page, int cat) {
        return awakerApi.getSpecialList(token, page, cat)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<NewDetail>> getNewDetail(String token, String newId) {
        return awakerApi.getNewDetail(token, newId)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<SpecialDetail>> getSpecialDetail(String token, String id) {
        return awakerApi.getSpecialDetail(token, id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<List<Comment>>> getUpNewsComments(String token, String newId) {
        return awakerApi.getUpNewsComments(token, newId)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<List<Comment>>> getNewsComments(String token, String newId,
                                                               int page) {
        return awakerApi.getNewsComments(token, newId, page)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<List<News>>> getHotviewNewsAll(String token, int page, int id) {
        return awakerApi.getHotviewNewsAll(token, page, id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<List<News>>> getHotNewsAll(String token, int page, int id) {
        return awakerApi.getHotNewsAll(token, page, id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<List<Comment>>> getHotComment(String token) {
        return awakerApi.getHotComment(token)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<Object>> sendNewsComment(String token, String newId, String content, String open_id, String pid) {
        return awakerApi.sendNewsComment(token, newId, content, open_id, pid)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<Object>> register(String token, String email, String nickname, String password) {
        return awakerApi.register(token, email, nickname, password)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<UserInfo> login(String token, String username, String password) {
        return awakerApi.login(token, username, password)
                .subscribeOn(Schedulers.io());
    }
}
