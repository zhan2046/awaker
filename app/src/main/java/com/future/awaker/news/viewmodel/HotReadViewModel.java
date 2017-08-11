package com.future.awaker.news.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.future.awaker.base.viewmodel.BaseListViewModel;
import com.future.awaker.data.News;
import com.future.awaker.data.realm.NewsPageRealm;
import com.future.awaker.data.realm.NewsRealm;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.source.AwakerRepository;
import com.future.awaker.util.LogUtils;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public class HotReadViewModel extends BaseListViewModel {

    private static final String TAG = HotReadViewModel.class.getSimpleName();
    private static final String ID_VALUE = "hot_view_news_all";

    public ObservableList<News> news = new ObservableArrayList<>();
    private HashMap<String, String> map = new HashMap<>();

    public HotReadViewModel() {
        map.put(NewsPageRealm.ID, ID_VALUE);
    }

    @Override
    public void refreshData(boolean refresh) {
        if (isRefresh && news.isEmpty()) {
            getLocalHotViewNewsAll();
        }
        getRemoteHotViewNewsAll();
    }

    private void getRemoteHotViewNewsAll() {
        disposable.add(AwakerRepository.get().getHotviewNewsAll(TOKEN, page, 0)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> setRemoteHotViewNewsAll(result.getData()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void setRemoteHotViewNewsAll(List<News> newsList) {
        setDataList(newsList, news);

        if (!isEmpty.get()) {
            if (isRefresh) {
                NewsPageRealm newsPageRealm = new NewsPageRealm();
                newsPageRealm.setNews_page_id(ID_VALUE);
                RealmList<NewsRealm> realmList = NewsPageRealm.getNewsRealmList(news);
                newsPageRealm.setNewsList(realmList);
                AwakerRepository.get().updateLocalRealm(newsPageRealm);
            }

            // only one page
            isEmpty.set(true);
        }
    }

    private void getLocalHotViewNewsAll() {
        disposable.add(AwakerRepository.get().getLocalRealm(NewsPageRealm.class, map)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG, "doOnError: " + throwable.toString()))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(realmResults -> {
                    LogUtils.d("getLocalHotViewNewsAll" + realmResults.size());
                    setLocalHotViewNewsAll(realmResults);
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void setLocalHotViewNewsAll(RealmResults realmResults) {
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
