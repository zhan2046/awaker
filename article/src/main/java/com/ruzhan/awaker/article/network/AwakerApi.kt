package com.ruzhan.awaker.article.network


import com.ruzhan.awaker.article.model.BannerItem
import com.ruzhan.awaker.article.model.Comment
import com.ruzhan.awaker.article.model.NewDetail
import com.ruzhan.awaker.article.model.News
import com.ruzhan.awaker.article.model.Special
import com.ruzhan.awaker.article.model.SpecialDetail
import com.ruzhan.awaker.article.model.UserInfo

import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AwakerApi {

    @FormUrlEncoded
    @POST("news/getAdv")
    fun getBanner(@Field("access_token") token: String,
                  @Field("adv_type") advType: String): Flowable<HttpResult<List<BannerItem>>>

    // new

    @FormUrlEncoded
    @POST("news/getNewsAll")
    fun getNewList(@Field("access_token") token: String,
                   @Field("page") page: Int, @Field("id") id: Int): Flowable<HttpResult<List<News>>>

    @FormUrlEncoded
    @POST("news/getNewsDetail")
    fun getNewDetail(@Field("access_token") token: String,
                     @Field("id") newId: String): Flowable<HttpResult<NewDetail>>

    @FormUrlEncoded
    @POST("news/getHotViewNewsAll")
    fun getHotviewNewsAll(@Field("access_token") token: String,
                          @Field("page") page: Int, @Field("id") id: Int): Flowable<HttpResult<List<News>>>

    @FormUrlEncoded
    @POST("news/getHotNewsWeek")
    fun getHotNewsAll(@Field("access_token") token: String,
                      @Field("page") page: Int, @Field("id") id: Int): Flowable<HttpResult<List<News>>>

    // comment

    @FormUrlEncoded
    @POST("news/getupNewsComments")
    fun getUpNewsComments(@Field("access_token") token: String,
                          @Field("id") newId: String): Flowable<HttpResult<List<Comment>>>

    @FormUrlEncoded
    @POST("news/getNewsComments")
    fun getNewsComments(@Field("access_token") token: String,
                        @Field("id") newId: String,
                        @Field("page") page: Int): Single<HttpResult<List<Comment>>>

    @FormUrlEncoded
    @POST("news/GetHotComment")
    fun getHotComment(@Field("access_token") token: String): Flowable<HttpResult<List<Comment>>>

    // video

    @FormUrlEncoded
    @POST("special/getSpecialList")
    fun getSpecialList(@Field("access_token") token: String,
                       @Field("page") page: Int, @Field("cat") cat: Int): Flowable<HttpResult<List<Special>>>

    @FormUrlEncoded
    @POST("special/getSpecialDetail")
    fun getSpecialDetail(@Field("access_token") token: String,
                         @Field("id") id: String): Flowable<HttpResult<SpecialDetail>>


    @FormUrlEncoded
    @POST("news/sendNewsComment")
    fun sendNewsComment(@Field("access_token") token: String,
                        @Field("id") newId: String,
                        @Field("content") content: String,
                        @Field("open_id") open_id: String,
                        @Field("pid") pid: String): Flowable<HttpResult<Any>>

    @FormUrlEncoded
    @POST("account/register")
    fun register(@Field("access_token") token: String,
                 @Field("email") email: String,
                 @Field("nickname") nickname: String,
                 @Field("password") password: String): Flowable<HttpResult<Any>>

    @FormUrlEncoded
    @POST("account/login")
    fun login(@Field("access_token") token: String,
              @Field("username") username: String,
              @Field("password") password: String): Flowable<UserInfo>
}
