package com.future.awaker.news.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

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

public class HotReadViewModel extends BaseListViewModel {

    private static final String TAG = "HotReadViewModel";

    public ObservableList<News> news = new ObservableArrayList<>();

    private RefreshListModel<News> refreshListModel = new RefreshListModel<>();
    private List<News> hotNewsList = new ArrayList<>();
    private MutableLiveData<RefreshListModel<News>> hotNewsLiveData = new MutableLiveData<>();

    private Disposable localDisposable;


    public HotReadViewModel() {
        hotNewsLiveData.setValue(null);
    }

    public void initHotNewsList() {
        localDisposable = AwakerRepository.get().loadNewsEntity(NewsEntity.HOT_READ_NEWS_ALL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "initHotNewsList doOnError: " + throwable.toString()))
                .doOnNext(this::setHotNewsList)
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setHotNewsList(NewsEntity newsEntity) {
        if (newsEntity != null && hotNewsList.isEmpty()) {
            hotNewsList.addAll(newsEntity.newsList);
            refreshListModel.setRefreshType(hotNewsList);
            hotNewsLiveData.setValue(refreshListModel);
        }
        localDisposable.dispose();
    }

    @Override
    public void refreshData(boolean refresh) {
        AwakerRepository.get().getHotviewNewsAll(TOKEN, page, 0)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> setRefreshDataDoOnNext(refresh, result.getData()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setRefreshDataDoOnNext(boolean refresh, List<News> remoteHotNewsList) {
        if (refresh) {
            hotNewsList.clear();
            refreshListModel.setRefreshType();

        } else {
            refreshListModel.setUpdateType();
        }

        if (remoteHotNewsList == null || remoteHotNewsList.isEmpty()) {
            isEmpty.set(true);

        } else {
            isEmpty.set(false);
            hotNewsList.addAll(remoteHotNewsList);
        }

        refreshListModel.setList(hotNewsList);
        hotNewsLiveData.setValue(refreshListModel);

        setHotNewsListLocalDb(hotNewsList);
    }

    private void setHotNewsListLocalDb(List<News> localNewsList) {
        Flowable.create(e -> {
            NewsEntity newsEntity = new NewsEntity(NewsEntity.HOT_READ_NEWS_ALL,
                    localNewsList);
            AwakerRepository.get().insertNewsEntity(newsEntity);
            e.onComplete();

        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "setHotNewsListLocalDb doOnError: " + throwable.toString()))
                .doOnComplete(() -> {
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public MutableLiveData<RefreshListModel<News>> getHotNewsLiveData() {
        return hotNewsLiveData;
    }
}
