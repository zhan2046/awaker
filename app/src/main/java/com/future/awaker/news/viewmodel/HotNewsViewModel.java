package com.future.awaker.news.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.future.awaker.base.viewmodel.BaseListViewModel;
import com.future.awaker.data.News;
import com.future.awaker.data.other.RefreshListModel;
import com.future.awaker.db.entity.NewsEntity;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.source.AwakerRepository;
import com.future.awaker.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright ©2017 by ruzhan
 */

public class HotNewsViewModel extends BaseListViewModel {

    private static final String TAG = "HotNewsViewModel";

    private RefreshListModel<News> refreshListModel = new RefreshListModel<>();
    private MutableLiveData<RefreshListModel<News>> hotNewsListLiveData = new MutableLiveData<>();

    private Disposable localDisposable;

    public HotNewsViewModel() {
        hotNewsListLiveData.setValue(null);
    }

    public void initLocalHotList() {
        localDisposable = AwakerRepository.get().loadNewsEntity(NewsEntity.HOT_NEWS_ALL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "initLocalHotList doOnError: " + throwable.toString()))
                .doOnNext(this::setLocalHotList)
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setLocalHotList(NewsEntity newsEntity) {
        if (newsEntity != null && hotNewsListLiveData.getValue() == null) {
            refreshListModel.setRefreshType(newsEntity.newsList);
            hotNewsListLiveData.setValue(refreshListModel);
        }
        localDisposable.dispose();
    }

    @Override
    public void refreshData(boolean refresh) {
        AwakerRepository.get().getHotNewsAll(TOKEN, page, 0)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> setRefreshDataDoOnNext(refresh, result.getData()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setRefreshDataDoOnNext(boolean refresh, List<News> remoteNewsList) {
        if (refresh) {
            refreshListModel.setRefreshType();

        } else {
            refreshListModel.setUpdateType();
        }
        if (remoteNewsList != null) {
            refreshListModel.setList(remoteNewsList);
            hotNewsListLiveData.setValue(refreshListModel);

            // save news to local db
            setHotListLocalDb(remoteNewsList);
        }
    }

    private void setHotListLocalDb(List<News> localNewsList) {
        Flowable.create(e -> {
            NewsEntity newsEntity = new NewsEntity(NewsEntity.HOT_NEWS_ALL,
                    localNewsList);
            AwakerRepository.get().insertNewsEntity(newsEntity);
            e.onComplete();

        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "setHotListLocalDb doOnError: " + throwable.toString()))
                .doOnComplete(() -> {
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public MutableLiveData<RefreshListModel<News>> getHotNewsListLiveData() {
        return hotNewsListLiveData;
    }
}
