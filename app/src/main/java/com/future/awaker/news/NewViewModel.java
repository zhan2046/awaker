package com.future.awaker.news;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.future.awaker.base.BaseListViewModel;
import com.future.awaker.data.News;
import com.future.awaker.data.source.NewRepository;
import com.future.awaker.data.source.callback.NewCallBack;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class NewViewModel extends BaseListViewModel {

    private int newId;
    public ObservableList<News> news = new ObservableArrayList<>();

    public NewViewModel(int newId) {
        this.newId = newId;
    }

    @Override
    public void refreshData(boolean refresh) {
        getLocalNewList(newId);
        getRemoteNewList();
    }

    private void getRemoteNewList() {
        disposable.add(NewRepository.get().getNewList(TOKEN, page, newId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(httpResult -> {
                    List<News> newsList = httpResult.getData();
                    setNewList(newsList);
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void getLocalNewList(int newId) {
        NewRepository.get().getLocalNewList(newId, new NewCallBack() {
            @Override
            public void onNewListSuc(List<News> newsList) {
                setNewList(newsList);
            }

            @Override
            public void onNewListFail() {

            }
        });
    }

    private void setNewList(List<News> newsList) {
        checkEmpty(newsList);
        if (isRefresh) {
            news.clear();
        }
        if (!isEmpty.get()) {
            news.addAll(newsList);
        }
    }
}
