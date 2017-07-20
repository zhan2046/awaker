package com.future.awaker.news;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.future.awaker.base.BaseListViewModel;
import com.future.awaker.data.New;
import com.future.awaker.data.source.NewRepository;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.network.HttpResult;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class NewViewModel extends BaseListViewModel {

    private int newId;
    public ObservableList<New> news = new ObservableArrayList<>();

    public NewViewModel(int newId) {
        this.newId = newId;
    }

    @Override
    public void refreshData(boolean refresh) {
        disposable.add(NewRepository.get().getNewList(TOKEN, page, newId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(httpResult -> {
                    List<New> newList = httpResult.getData();
                    checkEmpty(newList);

                    if (isRefresh) {
                        news.clear();
                    }
                    if (!isEmpty.get()) {
                        news.addAll(httpResult.getData());
                    }
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }
}
