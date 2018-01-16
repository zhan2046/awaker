package com.future.awaker.news.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.future.awaker.base.viewmodel.BaseListViewModel;
import com.future.awaker.data.News;
import com.future.awaker.db.entity.NewsListEntity;
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
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class NewListViewModel extends BaseListViewModel {

    private static final String TAG = "NewListViewModel";

    private int newId;
    private MutableLiveData<List<News>> newsLiveData = new MutableLiveData<>();

    private Disposable localDisposable;

    public NewListViewModel(int newId) {
        this.newId = newId;

        newsLiveData.setValue(null);
    }

    public void initLocalNews() {
        localDisposable = AwakerRepository.get().loadNewsListEntity("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "initLocalNews doOnError: " + throwable.toString()))
                .doOnNext(this::setLocalNewsList)
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setLocalNewsList(NewsListEntity newsListEntity) {
        if (newsListEntity != null && newsLiveData.getValue() == null) {
            newsLiveData.setValue(newsListEntity.newsList);
            localDisposable.dispose();
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
                .doOnNext(news -> refreshDataOnNext(news, refresh, page))
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void refreshDataOnNext(List<News> news, boolean refresh, int page) {
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
        setNewsToLocalDb(news, page);
    }

    private void setNewsToLocalDb(List<News> news, int page) {
        Flowable.create(e -> saveNewsToLocal(news, page, e), BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "setNewsToLocalDb doOnError: " + throwable.toString()))
                .doOnComplete(() -> {
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void saveNewsToLocal(List<News> news, int page, FlowableEmitter e) {
        List<News> localNewsList = new ArrayList<>(news);
        NewsListEntity newsListEntity = new NewsListEntity(String.valueOf(page),
                localNewsList);
        AwakerRepository.get().insertNewsListEntity(newsListEntity);
        e.onComplete();
    }

    public LiveData<List<News>> getNewsLiveData() {
        return newsLiveData;
    }
}
