package com.future.awaker.news;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import com.future.awaker.base.BaseListViewModel;
import com.future.awaker.data.News;
import com.future.awaker.data.realm.NewsPageRealm;
import com.future.awaker.data.realm.NewsRealm;
import com.future.awaker.data.source.NewRepository;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.RealmList;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class NewViewModel extends BaseListViewModel {

    public ObservableList<News> news = new ObservableArrayList<>();
    private int newId;
    private HashMap<String, String> map = new HashMap<>();

    public NewViewModel(int newId) {
        this.newId = newId;
        map.put("id", String.valueOf(newId));
    }

    @Override
    public void refreshData(boolean refresh) {
        if (refresh) {
            getLocalNewList();
        }
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
                    checkEmpty(newsList);

                    if (!isEmpty.get()) {
                        // save to local
                        NewRepository.get().updateLocalNewList(String.valueOf(newId), newsList);
                        setNewList(newsList);
                    }
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void getLocalNewList() {
        disposable.add(NewRepository.get().getLocalNewList(map)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable ->  Log.i("getLocalNewList","doOnError"))
                .doOnNext(results -> {
                    Log.i("getLocalNewList","doOnNext");

                    if (results != null && !results.isEmpty()) {
                        NewsPageRealm newsPageRealm = (NewsPageRealm) results.get(0);

                        //data is empty, network not back
                        if (news.isEmpty()) {
                            RealmList<NewsRealm> realmList = newsPageRealm.getNewsList();
                            List<News> newsList = NewsPageRealm.getNewsList(realmList);
                            setNewList(newsList);
                        }
                    }
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
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
