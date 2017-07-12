package com.future.awaker.news;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.text.TextUtils;

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

    private NewRepository newRepository;

    private String token;
    private int id;

    public NewViewModel(NewRepository newRepository) {
        this.newRepository = newRepository;
    }

    public void setToken(String token, int id) {
        this.token = token;
        this.id = id;
    }

    @Override
    public void fetchData(boolean isRefresh, int page) {
        if (TextUtils.isEmpty(token) || isRunning.get()) {
            return;
        }
        disposable.add(newRepository.getNewList(token, page, id)
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
                    news.addAll(httpResult.getData());
                })
                .subscribe());
    }


}
