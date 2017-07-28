package com.future.awaker.news;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.future.awaker.base.BaseListViewModel;
import com.future.awaker.data.News;
import com.future.awaker.data.realm.NewsPageRealm;
import com.future.awaker.data.realm.NewsRealm;
import com.future.awaker.data.source.NewRepository;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.util.LogUtils;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public class HotNewsViewModel extends BaseListViewModel {

    private static final String TAG = HotNewsViewModel.class.getSimpleName();
    private static final String ID_VALUE = "hot_news_all";

    public ObservableList<News> news = new ObservableArrayList<>();
    private HashMap<String, String> map = new HashMap<>();

    public HotNewsViewModel() {
        map.put(NewsPageRealm.ID, ID_VALUE);
    }

    @Override
    public void refreshData(boolean refresh) {
        if (isRefresh && news.isEmpty()) {
            getLocalHotNewsAll();
        }
        getRemoteHotNewsAll();
    }

    private void getRemoteHotNewsAll() {
        disposable.add(NewRepository.get().getHotNewsAll(TOKEN, page, 0)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> setRemoteHotNewsAll(result.getData()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void setRemoteHotNewsAll(List<News> newsList) {
        checkEmpty(newsList);
        if (!isEmpty.get()) {
            if (isRefresh) {
                news.clear();

                NewsPageRealm newsPageRealm = new NewsPageRealm();
                newsPageRealm.setNews_page_id(ID_VALUE);
                RealmList<NewsRealm> realmList = NewsPageRealm.getNewsRealmList(newsList);
                newsPageRealm.setNewsList(realmList);
                NewRepository.get().updateLocalRealm(newsPageRealm);
            }

            news.addAll(newsList);
            isEmpty.set(true);
        }
    }

    private void getLocalHotNewsAll() {
        disposable.add(NewRepository.get().getLocalRealm(NewsPageRealm.class, map)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG, "doOnError: " + throwable.toString()))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(realmResults -> {
                    LogUtils.d("getLocalHotNewsAll" + realmResults.size());
                    setLocalHotNewsAll(realmResults);
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void setLocalHotNewsAll(RealmResults realmResults) {
        if (realmResults == null || realmResults.isEmpty()) {
            return;
        }
        NewsPageRealm newsPageRealm = (NewsPageRealm) realmResults.get(0);
        if (news.isEmpty()) {   // data is empty, network not back
            RealmList<NewsRealm> realmList = newsPageRealm.getNewsList();
            List<News> newsList = NewsPageRealm.getNewsList(realmList);

            news.addAll(newsList);
            isEmpty.set(true);
        }
    }
}
