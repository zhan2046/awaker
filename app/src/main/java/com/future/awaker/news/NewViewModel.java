package com.future.awaker.news;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.future.awaker.base.BaseListViewModel;
import com.future.awaker.data.New;
import com.future.awaker.data.source.NewRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class NewViewModel extends BaseListViewModel {

    public ObservableList<New> news = new ObservableArrayList<>();

    @Override
    public void fetchData(boolean isRefresh, int page) {
        if (isRunning.get()) {
            return;
        }
        disposable.add(NewRepository.get().getNewList(TOKEN, page, 0)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(httpResult -> {
                    List<New> newList = httpResult.getData();
                    notifyEmpty(newList);

                    if (isRefresh) {
                        news.clear();
                    }
                    if (!isEmpty.get()) {
                        news.addAll(httpResult.getData());
                    }
                })
                .subscribe());
    }
}
