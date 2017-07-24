package com.future.awaker.news;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.future.awaker.base.BaseListViewModel;
import com.future.awaker.data.News;
import com.future.awaker.data.source.NewRepository;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Copyright ©2017 by Teambition
 */

public class HotNewsViewModel extends BaseListViewModel {

    public ObservableList<News> news = new ObservableArrayList<>();

    @Override
    public void refreshData(boolean refresh) {
        disposable.add(NewRepository.get().getHotNewsAll(TOKEN, page, 0)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(httpResult -> {
                    List<News> newsList = httpResult.getData();
                    checkEmpty(newsList);
                    if (isRefresh) {
                        news.clear();
                    }
                    if (!isEmpty.get()) {
                        news.addAll(newsList);
                        isEmpty.set(true);
                    }
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }
}
