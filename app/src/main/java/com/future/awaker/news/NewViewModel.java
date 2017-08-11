package com.future.awaker.news;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.future.awaker.base.BaseListViewModel;
import com.future.awaker.data.News;
import com.future.awaker.data.realm.NewsPageRealm;
import com.future.awaker.data.realm.NewsRealm;
import com.future.awaker.data.source.AwakerRepository;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.util.LogUtils;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class NewViewModel extends BaseListViewModel {

    private static final String TAG = NewViewModel.class.getSimpleName();

    public ObservableList<News> news = new ObservableArrayList<>();
    private int newId;
    private HashMap<String, String> map = new HashMap<>();

    public NewViewModel(int newId) {
        this.newId = newId;
        map.put(NewsPageRealm.ID, String.valueOf(newId));
    }

    @Override
    public void refreshData(boolean refresh) {
        if (refresh && news.isEmpty()) {
            getLocalNewList();
        }
        getRemoteNewList();
    }

    private void getRemoteNewList() {
        disposable.add(AwakerRepository.get().getNewList(TOKEN, page, newId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(httpResult -> setRemoteNewList(httpResult.getData()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void getLocalNewList() {
        disposable.add(AwakerRepository.get().getLocalRealm(NewsPageRealm.class, map)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG, "doOnError: " + throwable.toString()))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(realmResults -> {
                    LogUtils.d("getLocalNewList" + realmResults.size());
                    setLocalNewList(realmResults);
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void setRemoteNewList(List<News> newsList) {
        setDataList(newsList, news);

        if (!isEmpty.get()) {
            // save to local
            NewsPageRealm newsPageRealm = new NewsPageRealm();
            RealmList<NewsRealm> realmList =
                    NewsPageRealm.getNewsRealmList(news);
            newsPageRealm.setNews_page_id(String.valueOf(newId));
            newsPageRealm.setNewsList(realmList);
            AwakerRepository.get().updateLocalRealm(newsPageRealm);
        }
    }

    private void setLocalNewList(RealmResults realmResults) {
        if (realmResults == null || realmResults.isEmpty()) {
            return;
        }
        NewsPageRealm newsPageRealm = (NewsPageRealm) realmResults.get(0);
        if (news.isEmpty()) {   // data is empty, network not back
            RealmList<NewsRealm> realmList = newsPageRealm.getNewsList();
            List<News> newsList = NewsPageRealm.getNewsList(realmList);
            setDataList(newsList, news);
        }
    }
}
