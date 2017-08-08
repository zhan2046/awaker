package com.future.awaker.network;

import com.future.awaker.data.BannerItem;
import com.future.awaker.data.Comment;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.News;
import com.future.awaker.data.Special;
import com.future.awaker.data.SpecialDetail;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Copyright ©2017 by Teambition
 */

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
    @POST("news/getNewsDetail/id/{newId}")
    Flowable<HttpResult<NewDetail>> getNewDetail(@Field("access_token") String token,
                                                 @Path("newId") String newId);

    @FormUrlEncoded
    @POST("news/getHotviewNewsAll")
    Flowable<HttpResult<List<News>>> getHotviewNewsAll(@Field("access_token") String token,
                                                       @Field("page") int page, @Field("id") int id);

    @FormUrlEncoded
    @POST("news/getHotNewsAll")
    Flowable<HttpResult<List<News>>> getHotNewsAll(@Field("access_token") String token,
                                                   @Field("page") int page, @Field("id") int id);

    // comment

    @FormUrlEncoded
    @POST("news/getupNewsComments/id/{newId}")
    Flowable<HttpResult<List<Comment>>> getUpNewsComments(@Field("access_token") String token,
                                                          @Path("newId") String newId);

    @FormUrlEncoded
    @POST("news/getNewsComments/id/{newId}")
    Flowable<HttpResult<List<Comment>>> getNewsComments(@Field("access_token") String token,
                                                        @Path("newId") String newId,
                                                        @Field("page") int page);

    @FormUrlEncoded
    @POST("news/hotComment")
    Flowable<HttpResult<List<Comment>>> getHotComment(@Field("access_token") String token);

    // video

    @FormUrlEncoded
    @POST("special/getSpecialList")
    Flowable<HttpResult<List<Special>>> getSpecialList(@Field("access_token") String token,
                                                       @Field("page") int page, @Field("cat") int cat);

    @FormUrlEncoded
    @POST("special/getSpecialDetail/id/{id}")
    Flowable<HttpResult<SpecialDetail>> getSpecialDetail(@Field("access_token") String token,
                                                         @Path("id") String id);


}
