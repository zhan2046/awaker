package com.future.awaker.news.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.future.awaker.base.viewmodel.BaseListViewModel;
import com.future.awaker.data.News;
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
import io.reactivex.FlowableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class NewListViewModel extends BaseListViewModel {

    private static final String TAG = "NewListViewModel";

    private int newId;
    private MutableLiveData<List<News>> newsLiveData = new MutableLiveData<>();

    public NewListViewModel(int newId) {
        this.newId = newId;

        newsLiveData.setValue(null);
    }

    public void initLocalNews() {
        AwakerRepository.get().loadAllNewsEntitys()
                .map(NewsEntity::getNewsList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "initLocalNews doOnError: " + throwable.toString()))
                .doOnNext(this::setLocalNews)
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setLocalNews(List<News> localNewsList) {
        if (localNewsList != null && newsLiveData.getValue() == null) {
            newsLiveData.setValue(localNewsList);
        }
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
        List<News> newsList = newsLiveData.getValue();
        if (newsList == null) {
            newsList = new ArrayList<>();
        }
        if (refresh) {
            newsList.clear();
        }
        newsList.addAll(news);
        newsLiveData.setValue(newsList);

        // save news to local db
        setNewsToLocalDb(news);
    }

    private void setNewsToLocalDb(List<News> news) {
        Flowable.create(e -> saveNewsToLocal(news, e), BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "setNewsToLocalDb doOnError: " + throwable.toString()))
                .doOnComplete(() -> {})
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void saveNewsToLocal(List<News> news, FlowableEmitter e) {
        List<News> localNewsList = new ArrayList<>(news);
        List<NewsEntity> newsEntities =
                NewsEntity.getNewsEntityList(localNewsList);
        if (newsEntities != null && !newsEntities.isEmpty()) {
            AwakerRepository.get().insertAll(newsEntities);
        }
        e.onComplete();
    }

    public LiveData<List<News>> getNewsLiveData() {
        return newsLiveData;
    }
}
