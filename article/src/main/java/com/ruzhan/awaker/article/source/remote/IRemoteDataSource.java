package com.ruzhan.awaker.article.source.remote;


import com.ruzhan.awaker.article.model.BannerItem;
import com.ruzhan.awaker.article.model.Comment;
import com.ruzhan.awaker.article.model.NewDetail;
import com.ruzhan.awaker.article.model.News;
import com.ruzhan.awaker.article.model.Special;
import com.ruzhan.awaker.article.model.SpecialDetail;
import com.ruzhan.awaker.article.model.UserInfo;
import com.ruzhan.awaker.article.network.HttpResult;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Copyright ©2017 by ruzhan
 */


public interface IRemoteDataSource {

    Flowable<HttpResult<List<BannerItem>>> getBanner(String token, String advType);

    Flowable<HttpResult<List<News>>> getNewList(String token, int page, int id);

    Flowable<HttpResult<List<Special>>> getSpecialList(String token, int page, int cat);

    Flowable<HttpResult<NewDetail>> getNewDetail(String token, String newId);

    Flowable<HttpResult<SpecialDetail>> getSpecialDetail(String token, String id);

    Flowable<HttpResult<List<Comment>>> getUpNewsComments(String token, String newId);

    Single<HttpResult<List<Comment>>> getNewsComments(String token, String newId,
                                                      int page);

    Flowable<HttpResult<List<News>>> getHotviewNewsAll(String token, int page, int id);

    Flowable<HttpResult<List<News>>> getHotNewsAll(String token, int page, int id);

    Flowable<HttpResult<List<Comment>>> getHotComment(String token);

    Flowable<HttpResult<Object>> sendNewsComment(String token, String newId, String content, String open_id,
                                                 String pid);

    Flowable<HttpResult<Object>> register(String token, String email, String nickname, String password);

    Flowable<UserInfo> login(String token, String username, String password);
}
