package com.ruzhan.awaker.article.source.remote

import com.ruzhan.awaker.article.model.BannerItem
import com.ruzhan.awaker.article.model.Comment
import com.ruzhan.awaker.article.model.NewDetail
import com.ruzhan.awaker.article.model.News
import com.ruzhan.awaker.article.model.Special
import com.ruzhan.awaker.article.model.SpecialDetail
import com.ruzhan.awaker.article.model.UserInfo
import com.ruzhan.awaker.article.network.HttpResult

import io.reactivex.Flowable
import io.reactivex.Single


interface IRemoteDataSource {

    fun getBanner(token: String, advType: String): Flowable<HttpResult<List<BannerItem>>>

    fun getNewList(token: String, page: Int, id: Int): Flowable<HttpResult<List<News>>>

    fun getSpecialList(token: String, page: Int, cat: Int): Flowable<HttpResult<List<Special>>>

    fun getNewDetail(token: String, newId: String): Flowable<HttpResult<NewDetail>>

    fun getSpecialDetail(token: String, id: String): Flowable<HttpResult<SpecialDetail>>

    fun getUpNewsComments(token: String, newId: String): Flowable<HttpResult<List<Comment>>>

    fun getNewsComments(token: String, newId: String,
                        page: Int): Single<HttpResult<List<Comment>>>

    fun getHotviewNewsAll(token: String, page: Int, id: Int): Flowable<HttpResult<List<News>>>

    fun getHotNewsAll(token: String, page: Int, id: Int): Flowable<HttpResult<List<News>>>

    fun getHotComment(token: String): Flowable<HttpResult<List<Comment>>>

    fun sendNewsComment(token: String, newId: String, content: String, open_id: String,
                        pid: String): Flowable<HttpResult<Any>>

    fun register(token: String, email: String, nickname: String, password: String): Flowable<HttpResult<Any>>

    fun login(token: String, username: String, password: String): Flowable<UserInfo>
}
