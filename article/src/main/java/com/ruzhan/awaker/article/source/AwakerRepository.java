package com.ruzhan.awaker.article.source;


import com.ruzhan.awaker.article.App;
import com.ruzhan.awaker.article.db.AwakerArticleAppDatabase;
import com.ruzhan.awaker.article.db.entity.CommentEntity;
import com.ruzhan.awaker.article.db.entity.NewsEntity;
import com.ruzhan.awaker.article.db.entity.SpecialListEntity;
import com.ruzhan.awaker.article.db.entity.UserInfoEntity;
import com.ruzhan.awaker.article.model.BannerItem;
import com.ruzhan.awaker.article.model.Comment;
import com.ruzhan.awaker.article.model.NewDetail;
import com.ruzhan.awaker.article.model.News;
import com.ruzhan.awaker.article.model.Special;
import com.ruzhan.awaker.article.model.SpecialDetail;
import com.ruzhan.awaker.article.model.UserInfo;
import com.ruzhan.awaker.article.network.AwakerClient;
import com.ruzhan.awaker.article.network.HttpResult;
import com.ruzhan.awaker.article.source.remote.IRemoteDataSource;
import com.ruzhan.awaker.article.source.remote.RemoteDataSourceImpl;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Copyright ©2017 by ruzhan
 */

public final class AwakerRepository implements IRemoteDataSource {

    private static AwakerRepository INSTANCE;

    private IRemoteDataSource remoteDataSource;
    private AwakerArticleAppDatabase awakerArticleAppDatabase;

    private AwakerRepository() {
        remoteDataSource = new RemoteDataSourceImpl(AwakerClient.get());
        awakerArticleAppDatabase = AwakerArticleAppDatabase.get(App.get());
    }

    public static AwakerRepository get() {
        if (INSTANCE == null) {
            synchronized (AwakerRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AwakerRepository();
                }
            }
        }
        return INSTANCE;
    }

    public Flowable<UserInfoEntity> loadUserInfoEntity(String id) {
        return awakerArticleAppDatabase.userInfoDao().loadUserInfoEntity(id);
    }

    public void insertUserInfoEntity(UserInfoEntity userInfoEntity) {
        awakerArticleAppDatabase.userInfoDao().insertUserInfoEntity(userInfoEntity);
    }

    public Flowable<SpecialDetail> loadSpecialDetail(String id) {
        return awakerArticleAppDatabase.specialDetailDao().loadSpecialDetail(id);
    }

    public void insertSpecialDetail(SpecialDetail specialDetail) {
        awakerArticleAppDatabase.specialDetailDao().insertSpecialDetail(specialDetail);
    }

    public Flowable<NewsEntity> loadNewsEntity(String id) {
        return awakerArticleAppDatabase.newsListDao().loadNewsEntity(id);
    }

    public void insertNewsEntity(NewsEntity newsEntity) {
        awakerArticleAppDatabase.newsListDao().insertNewsEntity(newsEntity);
    }

    public Flowable<CommentEntity> loadCommentEntity(String id) {
        return awakerArticleAppDatabase.commentListDao().loadCommentEntity(id);
    }

    public void insertCommentEntity(CommentEntity commentEntity) {
        awakerArticleAppDatabase.commentListDao().insertCommentEntity(commentEntity);
    }

    public Flowable<NewDetail> loadNewsDetail(String id) {
        return awakerArticleAppDatabase.newDetailDao().loadNewsDetail(id);
    }

    public void insertNewsDetail(NewDetail newDetail) {
        awakerArticleAppDatabase.newDetailDao().insertNewsDetail(newDetail);
    }

    public Flowable<List<BannerItem>> loadAllBanners() {
        return awakerArticleAppDatabase.bannerDao().loadAllBanners();
    }

    public void insertAllBanners(List<BannerItem> bannerItems) {
        awakerArticleAppDatabase.bannerDao().insertAllBanners(bannerItems);
    }

    public Flowable<SpecialListEntity> loadSpecialListEntity(String id) {
        return awakerArticleAppDatabase.specialListDao().loadSpecialListEntity(id);
    }

    public void insertAll(SpecialListEntity specialListEntity) {
        awakerArticleAppDatabase.specialListDao().insertAll(specialListEntity);
    }

    public Flowable<List<News>> loadNewsList() {
        return awakerArticleAppDatabase.newsDao().loadNewsList();
    }

    public void insertNewsList(List<News> newsList) {
        awakerArticleAppDatabase.newsDao().insertNewsList(newsList);
    }

    @Override
    public Flowable<HttpResult<List<BannerItem>>> getBanner(String token, String advType) {
        return remoteDataSource.getBanner(token, advType);
    }

    @Override
    public Flowable<HttpResult<List<News>>> getNewList(String token, int page, int id) {
        return remoteDataSource.getNewList(token, page, id);
    }

    @Override
    public Flowable<HttpResult<List<Special>>> getSpecialList(String token, int page, int cat) {
        return remoteDataSource.getSpecialList(token, page, cat);
    }

    @Override
    public Flowable<HttpResult<NewDetail>> getNewDetail(String token, String newId) {
        return remoteDataSource.getNewDetail(token, newId);
    }

    @Override
    public Flowable<HttpResult<SpecialDetail>> getSpecialDetail(String token, String id) {
        return remoteDataSource.getSpecialDetail(token, id);
    }

    @Override
    public Flowable<HttpResult<List<Comment>>> getUpNewsComments(String token, String newId) {
        return remoteDataSource.getUpNewsComments(token, newId);
    }

    @Override
    public Single<HttpResult<List<Comment>>> getNewsComments(String token, String newId,
                                                             int page) {
        return remoteDataSource.getNewsComments(token, newId, page);
    }

    @Override
    public Flowable<HttpResult<List<News>>> getHotviewNewsAll(String token, int page, int id) {
        return remoteDataSource.getHotviewNewsAll(token, page, id);
    }

    @Override
    public Flowable<HttpResult<List<News>>> getHotNewsAll(String token, int page, int id) {
        return remoteDataSource.getHotNewsAll(token, page, id);
    }

    @Override
    public Flowable<HttpResult<List<Comment>>> getHotComment(String token) {
        return remoteDataSource.getHotComment(token);
    }

    @Override
    public Flowable<HttpResult<Object>> sendNewsComment(String token, String newId, String content,
                                                        String open_id, String pid) {
        return remoteDataSource.sendNewsComment(token, newId, content, open_id, pid);
    }

    @Override
    public Flowable<HttpResult<Object>> register(String token, String email, String nickname,
                                                 String password) {
        return remoteDataSource.register(token, email, nickname, password);
    }

    @Override
    public Flowable<UserInfo> login(String token, String username, String password) {
        return remoteDataSource.login(token, username, password);
    }
}
