package com.ruzhan.awaker.article.source

import com.ruzhan.awaker.article.App
import com.ruzhan.awaker.article.db.AwakerArticleAppDatabase
import com.ruzhan.awaker.article.db.entity.CommentEntity
import com.ruzhan.awaker.article.db.entity.NewsEntity
import com.ruzhan.awaker.article.db.entity.SpecialListEntity
import com.ruzhan.awaker.article.db.entity.UserInfoEntity
import com.ruzhan.awaker.article.model.BannerItem
import com.ruzhan.awaker.article.model.Comment
import com.ruzhan.awaker.article.model.NewDetail
import com.ruzhan.awaker.article.model.News
import com.ruzhan.awaker.article.model.Special
import com.ruzhan.awaker.article.model.SpecialDetail
import com.ruzhan.awaker.article.model.UserInfo
import com.ruzhan.awaker.article.network.AwakerClient
import com.ruzhan.awaker.article.network.HttpResult
import com.ruzhan.awaker.article.source.remote.IRemoteDataSource
import com.ruzhan.awaker.article.source.remote.RemoteDataSourceImpl

import io.reactivex.Flowable
import io.reactivex.Single

class AwakerRepository private constructor() : IRemoteDataSource {

    private val remoteDataSource: IRemoteDataSource
    private val awakerArticleAppDatabase: AwakerArticleAppDatabase

    init {
        remoteDataSource = RemoteDataSourceImpl(AwakerClient.get())
        awakerArticleAppDatabase = AwakerArticleAppDatabase.get(App.get())
    }

    fun loadUserInfoEntity(id: String): Flowable<UserInfoEntity> {
        return awakerArticleAppDatabase.userInfoDao().loadUserInfoEntity(id)
    }

    fun insertUserInfoEntity(userInfoEntity: UserInfoEntity) {
        awakerArticleAppDatabase.userInfoDao().insertUserInfoEntity(userInfoEntity)
    }

    fun loadSpecialDetail(id: String): Flowable<SpecialDetail> {
        return awakerArticleAppDatabase.specialDetailDao().loadSpecialDetail(id)
    }

    fun insertSpecialDetail(specialDetail: SpecialDetail) {
        awakerArticleAppDatabase.specialDetailDao().insertSpecialDetail(specialDetail)
    }

    fun loadNewsEntity(id: String): Flowable<NewsEntity> {
        return awakerArticleAppDatabase.newsListDao().loadNewsEntity(id)
    }

    fun insertNewsEntity(newsEntity: NewsEntity) {
        awakerArticleAppDatabase.newsListDao().insertNewsEntity(newsEntity)
    }

    fun loadCommentEntity(id: String): Flowable<CommentEntity> {
        return awakerArticleAppDatabase.commentListDao().loadCommentEntity(id)
    }

    fun insertCommentEntity(commentEntity: CommentEntity) {
        awakerArticleAppDatabase.commentListDao().insertCommentEntity(commentEntity)
    }

    fun loadNewsDetail(id: String): Flowable<NewDetail> {
        return awakerArticleAppDatabase.newDetailDao().loadNewsDetail(id)
    }

    fun insertNewsDetail(newDetail: NewDetail) {
        awakerArticleAppDatabase.newDetailDao().insertNewsDetail(newDetail)
    }

    fun loadAllBanners(): Flowable<List<BannerItem>> {
        return awakerArticleAppDatabase.bannerDao().loadAllBanners()
    }

    fun insertAllBanners(bannerItems: List<BannerItem>) {
        awakerArticleAppDatabase.bannerDao().insertAllBanners(bannerItems)
    }

    fun loadSpecialListEntity(id: String): Flowable<SpecialListEntity> {
        return awakerArticleAppDatabase.specialListDao().loadSpecialListEntity(id)
    }

    fun insertAll(specialListEntity: SpecialListEntity) {
        awakerArticleAppDatabase.specialListDao().insertAll(specialListEntity)
    }

    fun loadNewsList(): Flowable<List<News>> {
        return awakerArticleAppDatabase.newsDao().loadNewsList()
    }

    fun insertNewsList(newsList: List<News>) {
        awakerArticleAppDatabase.newsDao().insertNewsList(newsList)
    }

    override fun getBanner(token: String, advType: String): Flowable<HttpResult<List<BannerItem>>> {
        return remoteDataSource.getBanner(token, advType)
    }

    override fun getNewList(token: String, page: Int, id: Int): Flowable<HttpResult<List<News>>> {
        return remoteDataSource.getNewList(token, page, id)
    }

    override fun getSpecialList(token: String, page: Int, cat: Int): Flowable<HttpResult<List<Special>>> {
        return remoteDataSource.getSpecialList(token, page, cat)
    }

    override fun getNewDetail(token: String, newId: String): Flowable<HttpResult<NewDetail>> {
        return remoteDataSource.getNewDetail(token, newId)
    }

    override fun getSpecialDetail(token: String, id: String): Flowable<HttpResult<SpecialDetail>> {
        return remoteDataSource.getSpecialDetail(token, id)
    }

    override fun getUpNewsComments(token: String, newId: String): Flowable<HttpResult<List<Comment>>> {
        return remoteDataSource.getUpNewsComments(token, newId)
    }

    override fun getNewsComments(token: String, newId: String,
                                 page: Int): Single<HttpResult<List<Comment>>> {
        return remoteDataSource.getNewsComments(token, newId, page)
    }

    override fun getHotviewNewsAll(token: String, page: Int, id: Int): Flowable<HttpResult<List<News>>> {
        return remoteDataSource.getHotviewNewsAll(token, page, id)
    }

    override fun getHotNewsAll(token: String, page: Int, id: Int): Flowable<HttpResult<List<News>>> {
        return remoteDataSource.getHotNewsAll(token, page, id)
    }

    override fun getHotComment(token: String): Flowable<HttpResult<List<Comment>>> {
        return remoteDataSource.getHotComment(token)
    }

    override fun sendNewsComment(token: String, newId: String, content: String,
                                 open_id: String, pid: String): Flowable<HttpResult<Any>> {
        return remoteDataSource.sendNewsComment(token, newId, content, open_id, pid)
    }

    override fun register(token: String, email: String, nickname: String,
                          password: String): Flowable<HttpResult<Any>> {
        return remoteDataSource.register(token, email, nickname, password)
    }

    override fun login(token: String, username: String, password: String): Flowable<UserInfo> {
        return remoteDataSource.login(token, username, password)
    }

    companion object {

        private var INSTANCE: AwakerRepository? = null

        fun get(): AwakerRepository {
            if (INSTANCE == null) {
                synchronized(AwakerRepository::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = AwakerRepository()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
