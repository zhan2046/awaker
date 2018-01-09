package com.future.awaker.news.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.future.awaker.base.viewmodel.BaseListViewModel;
import com.future.awaker.data.News;
import com.future.awaker.db.entity.NewsEntity;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.network.HttpResult;
import com.future.awaker.source.AwakerRepository;
import com.future.awaker.source.DataRepository;
import com.future.awaker.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class NewListViewModel extends BaseListViewModel {

    private static final String TAG = "NewListViewModel";

    private int newId;
    private MediatorLiveData<List<News>> newsLiveData = new MediatorLiveData<>();
    private DataRepository repository;

    public NewListViewModel() {
        repository = DataRepository.get();

        newsLiveData.setValue(null);
        newsLiveData.addSource(repository.loadAllNewsEntitys(),
                this::setLocalNewsEntities);
    }

    private void setLocalNewsEntities(List<NewsEntity> newsEntities) {
        if (newsEntities != null && !newsEntities.isEmpty() && newsLiveData.getValue() == null) {
            List<News> localNewsList = NewsEntity.getNewsList(newsEntities);
            if (localNewsList != null && newsLiveData.getValue() == null) {
                newsLiveData.postValue(localNewsList);
            }
        }
    }

    public LiveData<List<News>> getNewsLiveData() {
        return newsLiveData;
    }

    public void setNewId(int newId) {
        this.newId = newId;
    }

    @Override
    public void refreshData(boolean refresh) {
        AwakerRepository.get().getNewList(TOKEN, page, newId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    LogUtils.showLog(TAG, "refreshData doOnError called..." + throwable.toString());
                    isError.set(throwable);
                })
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .map(HttpResult::getData)
                .doOnNext(news -> {
                    List<News> newsList = newsLiveData.getValue();
                    if (newsList == null) {
                        newsList = new ArrayList<>();
                    }
                    if (refresh) {
                        newsList.clear();
                    }
                    newsList.addAll(news);
                    newsLiveData.setValue(newsList);

                    // save to local db
                    setLocalData(news);
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setLocalData(List<News> news) {
        long time = System.currentTimeMillis();
        Flowable.create(e -> {
            LogUtils.showLog(TAG, "thread name:" + Thread.currentThread().getName());

            List<News> localNewsList = new ArrayList<>(news);
            List<NewsEntity> newsEntities =
                    NewsEntity.getNewsEntityList(localNewsList);
            if (newsEntities != null && !newsEntities.isEmpty()) {
                repository.insertAll(newsEntities);
            }
            e.onComplete();

        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "doOnError setLocalData: " + throwable.toString()))
                .doOnComplete(() -> {
                    LogUtils.showLog(TAG,
                            "setLocalData time:" + (System.currentTimeMillis() - time) + "/ms");
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }
}
