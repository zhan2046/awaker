package com.ruzhan.awaker.article.source.remote;


import com.ruzhan.awaker.article.model.BannerItem;
import com.ruzhan.awaker.article.model.Comment;
import com.ruzhan.awaker.article.model.NewDetail;
import com.ruzhan.awaker.article.model.News;
import com.ruzhan.awaker.article.model.Special;
import com.ruzhan.awaker.article.model.SpecialDetail;
import com.ruzhan.awaker.article.model.UserInfo;
import com.ruzhan.awaker.article.network.AwakerApi;
import com.ruzhan.awaker.article.network.HttpResult;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;


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
        return awakerApi.getSpecialList(token, page, cat).subscribeOn(Schedulers.io());
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
    public Single<HttpResult<List<Comment>>> getNewsComments(String token, String newId, int page) {
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
    public Flowable<HttpResult<Object>> sendNewsComment(String token, String newId, String content,
                                                        String open_id, String pid) {
        return awakerApi.sendNewsComment(token, newId, content, open_id, pid)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<Object>> register(String token, String email, String nickname,
                                                 String password) {
        return awakerApi.register(token, email, nickname, password)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<UserInfo> login(String token, String username, String password) {
        return awakerApi.login(token, username, password)
                .subscribeOn(Schedulers.io());
    }
}
