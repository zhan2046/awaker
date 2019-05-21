package com.ruzhan.awaker.article.network;


import com.ruzhan.awaker.article.model.BannerItem;
import com.ruzhan.awaker.article.model.Comment;
import com.ruzhan.awaker.article.model.NewDetail;
import com.ruzhan.awaker.article.model.News;
import com.ruzhan.awaker.article.model.Special;
import com.ruzhan.awaker.article.model.SpecialDetail;
import com.ruzhan.awaker.article.model.UserInfo;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AwakerApi {

    @FormUrlEncoded
    @POST("news/getAdv")
    Flowable<HttpResult<List<BannerItem>>> getBanner(@Field("access_token") String token,
                                                     @Field("adv_type") String advType);

    // new

    @FormUrlEncoded
    @POST("news/getNewsAll")
    Flowable<HttpResult<List<News>>> getNewList(@Field("access_token") String token,
                                                @Field("page") int page, @Field("id") int id);

    @FormUrlEncoded
    @POST("news/getNewsDetail")
    Flowable<HttpResult<NewDetail>> getNewDetail(@Field("access_token") String token,
                                                 @Field("id") String newId);

    @FormUrlEncoded
    @POST("news/getHotViewNewsAll")
    Flowable<HttpResult<List<News>>> getHotviewNewsAll(@Field("access_token") String token,
                                                       @Field("page") int page, @Field("id") int id);

    @FormUrlEncoded
    @POST("news/getHotNewsWeek")
    Flowable<HttpResult<List<News>>> getHotNewsAll(@Field("access_token") String token,
                                                   @Field("page") int page, @Field("id") int id);

    // comment

    @FormUrlEncoded
    @POST("news/getupNewsComments")
    Flowable<HttpResult<List<Comment>>> getUpNewsComments(@Field("access_token") String token,
                                                          @Field("id") String newId);

    @FormUrlEncoded
    @POST("news/getNewsComments")
    Single<HttpResult<List<Comment>>> getNewsComments(@Field("access_token") String token,
                                                      @Field("id") String newId,
                                                      @Field("page") int page);

    @FormUrlEncoded
    @POST("news/GetHotComment")
    Flowable<HttpResult<List<Comment>>> getHotComment(@Field("access_token") String token);

    // video

    @FormUrlEncoded
    @POST("special/getSpecialList")
    Flowable<HttpResult<List<Special>>> getSpecialList(@Field("access_token") String token,
                                                       @Field("page") int page, @Field("cat") int cat);

    @FormUrlEncoded
    @POST("special/getSpecialDetail")
    Flowable<HttpResult<SpecialDetail>> getSpecialDetail(@Field("access_token") String token,
                                                         @Field("id") String id);


    @FormUrlEncoded
    @POST("news/sendNewsComment")
    Flowable<HttpResult<Object>> sendNewsComment(@Field("access_token") String token,
                                                 @Field("id") String newId,
                                                 @Field("content") String content,
                                                 @Field("open_id") String open_id,
                                                 @Field("pid") String pid);

    @FormUrlEncoded
    @POST("account/register")
    Flowable<HttpResult<Object>> register(@Field("access_token") String token,
                                          @Field("email") String email,
                                          @Field("nickname") String nickname,
                                          @Field("password") String password);

    @FormUrlEncoded
    @POST("account/login")
    Flowable<UserInfo> login(@Field("access_token") String token,
                             @Field("username") String username,
                             @Field("password") String password);
}
