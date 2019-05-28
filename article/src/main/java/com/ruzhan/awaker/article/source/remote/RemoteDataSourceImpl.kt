package com.ruzhan.awaker.article.source.remote


import com.ruzhan.awaker.article.model.BannerItem
import com.ruzhan.awaker.article.model.Comment
import com.ruzhan.awaker.article.model.NewDetail
import com.ruzhan.awaker.article.model.News
import com.ruzhan.awaker.article.model.Special
import com.ruzhan.awaker.article.model.SpecialDetail
import com.ruzhan.awaker.article.model.UserInfo
import com.ruzhan.awaker.article.network.AwakerApi
import com.ruzhan.awaker.article.network.HttpResult

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


class RemoteDataSourceImpl(private val awakerApi: AwakerApi) : IRemoteDataSource {

    override fun getBanner(token: String, advType: String): Flowable<HttpResult<List<BannerItem>>> {
        return awakerApi.getBanner(token, advType)
                .subscribeOn(Schedulers.io())
    }

    override fun getNewList(token: String, page: Int, id: Int): Flowable<HttpResult<List<News>>> {
        return awakerApi.getNewList(token, page, id)
                .subscribeOn(Schedulers.io())
    }

    override fun getSpecialList(token: String, page: Int, cat: Int): Flowable<HttpResult<List<Special>>> {
        return awakerApi.getSpecialList(token, page, cat).subscribeOn(Schedulers.io())
    }

    override fun getNewDetail(token: String, newId: String): Flowable<HttpResult<NewDetail>> {
        return awakerApi.getNewDetail(token, newId)
                .subscribeOn(Schedulers.io())
    }

    override fun getSpecialDetail(token: String, id: String): Flowable<HttpResult<SpecialDetail>> {
        return awakerApi.getSpecialDetail(token, id)
                .subscribeOn(Schedulers.io())
    }

    override fun getUpNewsComments(token: String, newId: String): Flowable<HttpResult<List<Comment>>> {
        return awakerApi.getUpNewsComments(token, newId)
                .subscribeOn(Schedulers.io())
    }

    override fun getNewsComments(token: String, newId: String, page: Int): Single<HttpResult<List<Comment>>> {
        return awakerApi.getNewsComments(token, newId, page)
                .subscribeOn(Schedulers.io())
    }

    override fun getHotviewNewsAll(token: String, page: Int, id: Int): Flowable<HttpResult<List<News>>> {
        return awakerApi.getHotviewNewsAll(token, page, id)
                .subscribeOn(Schedulers.io())
    }

    override fun getHotNewsAll(token: String, page: Int, id: Int): Flowable<HttpResult<List<News>>> {
        return awakerApi.getHotNewsAll(token, page, id)
                .subscribeOn(Schedulers.io())
    }

    override fun getHotComment(token: String): Flowable<HttpResult<List<Comment>>> {
        return awakerApi.getHotComment(token)
                .subscribeOn(Schedulers.io())
    }

    override fun sendNewsComment(token: String, newId: String, content: String,
                                 open_id: String, pid: String): Flowable<HttpResult<Any>> {
        return awakerApi.sendNewsComment(token, newId, content, open_id, pid)
                .subscribeOn(Schedulers.io())
    }

    override fun register(token: String, email: String, nickname: String,
                          password: String): Flowable<HttpResult<Any>> {
        return awakerApi.register(token, email, nickname, password)
                .subscribeOn(Schedulers.io())
    }

    override fun login(token: String, username: String, password: String): Flowable<UserInfo> {
        return awakerApi.login(token, username, password)
                .subscribeOn(Schedulers.io())
    }
}
