package com.love.awaker.network;

import com.love.awaker.data.New;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Copyright ©2017 by Teambition
 */

public interface AwakerApi {

    @FormUrlEncoded
    @POST("news/getNewsAll")
    Flowable<HttpResult<List<New>>> getNewList(@Field("access_token") String token,
                                               @Field("page") int page, @Field("id") int id);
}
