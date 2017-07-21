package com.future.awaker.data.source.local;

import android.util.Log;

import com.future.awaker.data.News;
import com.future.awaker.data.realm.NewsPageRealm;
import com.future.awaker.data.realm.NewsRealm;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.util.helper.RealmHelper;

import java.util.HashMap;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public class LocalNewDataSourceImpl implements LocalNewDataSource {

    private static final String AWAKER_DB = "awakerDB";
    private static final int VERSION_CODE = 0;


    public LocalNewDataSourceImpl() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(AWAKER_DB)
                .schemaVersion(VERSION_CODE)
                .build();
        //realmInstance = Realm.getInstance(config);
    }

    @Override
    public <T extends RealmModel> Flowable<RealmResults<T>> getLocalNewList(HashMap<String, String> map) {
        return Flowable.defer(() -> RealmHelper.getRealmItems(NewsPageRealm.class, map));
    }

    @Override
    public void deleteLocalNewList(String newId) {
//        realmInstance.where(NewListRealm.class)
//                .equalTo("id", newId)
//                .findAllAsync()
//                .addChangeListener((newBeen, changeSet) -> {
//                    if (newBeen.isLoaded()) {
//                        newBeen.deleteAllFromRealm();
//                    }
//                });
    }

    @Override
    public void updateLocalNewList(String newId, List<News> newsList) {
        Flowable.create((FlowableOnSubscribe<List<News>>) e -> {
            NewsPageRealm newsPageRealm = new NewsPageRealm();
            RealmList<NewsRealm> realmList =
                    NewsPageRealm.getNewsRealmList(newsList);
            newsPageRealm.setId(newId);
            newsPageRealm.setNewsList(realmList);

            Realm realmInstance = Realm.getDefaultInstance();
            realmInstance.executeTransaction(realm -> {
                realm.copyToRealmOrUpdate(newsPageRealm);
            });
        }, BackpressureStrategy.LATEST)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.i("updateLocalNewList", "doOnError" + throwable.toString()))
                .doOnNext(results -> Log.i("updateLocalNewList", "doOnNext"))
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public void onDestroy() {
        //realmInstance.close();
    }

}
