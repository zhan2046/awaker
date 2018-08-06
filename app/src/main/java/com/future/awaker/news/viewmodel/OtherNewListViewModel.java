package com.future.awaker.news.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.future.awaker.base.viewmodel.BaseListViewModel;
import com.future.awaker.data.News;
import com.future.awaker.data.other.RefreshListModel;
import com.future.awaker.db.entity.NewsEntity;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.network.HttpResult;
import com.future.awaker.source.AwakerRepository;
import com.future.awaker.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class OtherNewListViewModel extends BaseListViewModel {

    private static final String TAG = "NewListViewModel";

    private int newId;
    private RefreshListModel<News> refreshListModel = new RefreshListModel<>();

    private MutableLiveData<RefreshListModel<News>> newsLiveData = new MutableLiveData<>();

    private Disposable localDisposable;

    public OtherNewListViewModel(int newId) {
        this.newId = newId;
        newsLiveData.setValue(null);
    }

    public void initLocalNews() {
        localDisposable = AwakerRepository.get().loadNewsEntity(String.valueOf(newId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "initLocalNews doOnError: " + throwable.toString()))
                .doOnNext(this::setLocalNewsList)
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setLocalNewsList(NewsEntity newsEntity) {
        if (newsEntity != null && newsLiveData.getValue() == null) {
            refreshListModel.setRefreshType(newsEntity.newsList);
            newsLiveData.setValue(refreshListModel);
        }
        localDisposable.dispose();
    }

    @Override
    public void refreshData(boolean refresh) {
        AwakerRepository.get().getNewList(TOKEN, page, newId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "refreshData doOnError called..." + throwable.toString()))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .map(HttpResult::getData)
                .doOnNext(news -> refreshDataOnNext(news, refresh))
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void refreshDataOnNext(List<News> news, boolean refresh) {
        if (refresh) {
            refreshListModel.setRefreshType();

        } else {
            refreshListModel.setUpdateType();
        }
        if (news != null) {
            refreshListModel.setList(news);
            newsLiveData.setValue(refreshListModel);

            // save news to local db
            setNewsToLocalDb(news);
        }
    }

    private void setNewsToLocalDb(List<News> news) {
        Flowable.create(e -> {
            NewsEntity newsEntity = new NewsEntity(String.valueOf(newId), news);
            AwakerRepository.get().insertNewsEntity(newsEntity);
            e.onComplete();

        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "setNewsToLocalDb doOnError: " + throwable.toString()))
                .doOnComplete(() -> {
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public LiveData<RefreshListModel<News>> getNewsLiveData() {
        return newsLiveData;
    }
}
