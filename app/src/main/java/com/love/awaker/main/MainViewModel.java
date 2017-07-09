package com.love.awaker.main;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import android.util.Log;
import com.love.awaker.data.New;
import com.love.awaker.data.source.NewRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class MainViewModel extends BaseObservable {

    public ObservableBoolean isEmpty = new ObservableBoolean(false);
    public ObservableList<New> news = new ObservableArrayList<>();

    private boolean isLoaded;
    private NewRepository newRepository;
    private CompositeDisposable disposable = new CompositeDisposable();

    public MainViewModel(NewRepository newRepository) {
        this.newRepository = newRepository;
    }

    public void getNewList(String token, int page, int id) {
        if (isLoaded) {
            return;
        }
        disposable.add(newRepository.getNewList(token, page, id)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {})
                .doOnSubscribe(disposable -> isLoaded = true)
                .doOnTerminate(() -> isLoaded = false)
                .doOnNext(httpResult -> {
                    List<New> newList = httpResult.getData();
                    isEmpty.set(newList == null || newList.isEmpty());

                    news.clear();
                    news.addAll(httpResult.getData());
                  Log.i("news","news size:" + news.size());
                })
                .subscribe());
    }

    public void clear() {
        disposable.clear();
    }
}
