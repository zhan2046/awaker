package com.future.awaker.data.source.local;

import com.future.awaker.data.News;
import com.future.awaker.data.realm.NewsPageRealm;
import com.future.awaker.data.realm.NewsRealm;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.util.LogUtils;

import java.util.List;
import java.util.Map;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public final class RealmManager {

    private static final String TAG = RealmManager.class.getSimpleName();
    private static final String AWAKER_DB = "awakerDB";
    private static final int VERSION_CODE = 0;

    private Realm realmInstance;


    public RealmManager() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(AWAKER_DB)
                .schemaVersion(VERSION_CODE)
                .build();
        realmInstance = Realm.getInstance(config);
    }

    @SuppressWarnings("unchecked")
    public Flowable<RealmResults> getRealmItems(Class clazz,
                                                Map<String, String> map) {
        return Flowable.create(emitter -> {
            RealmQuery query = realmInstance.where(clazz);
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    query.equalTo(entry.getKey(), entry.getValue());
                }
            }
            RealmResults results = query.findAllAsync();
            RealmChangeListener<RealmResults> listener =
                    new RealmChangeListener<RealmResults>() {
                        @Override
                        public void onChange(RealmResults ts) {
                            if (ts.isLoaded()) {
                                emitter.onNext(results);
                                ts.removeChangeListener(this);
                            }
                        }
                    };
            results.addChangeListener(listener);
        }, BackpressureStrategy.LATEST);
    }

    public void updateLocalNewList(String newId, List<News> newsList) {
        Flowable.create((FlowableOnSubscribe<List<News>>) e -> {
            NewsPageRealm newsPageRealm = new NewsPageRealm();
            RealmList<NewsRealm> realmList =
                    NewsPageRealm.getNewsRealmList(newsList);
            newsPageRealm.setId(newId);
            newsPageRealm.setNewsList(realmList);

            realmInstance.executeTransaction(realm -> {
                realm.copyToRealmOrUpdate(newsPageRealm);
            });
        }, BackpressureStrategy.LATEST)
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "doOnError: " + throwable.toString()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    @SuppressWarnings("unchecked")
    public void deleteLocalNewList(String newId) {
        RealmResults results = realmInstance.where(NewsPageRealm.class)
                .equalTo(NewsPageRealm.ID, newId)
                .findAllAsync();
        RealmChangeListener<RealmResults> listener
                = new RealmChangeListener<RealmResults>() {
            @Override
            public void onChange(RealmResults realmResults) {
                if (realmResults.isLoaded()) {
                    realmResults.deleteAllFromRealm();
                    realmResults.removeChangeListener(this);
                }
            }
        };
        results.addChangeListener(listener);
    }

    public void close() {
        realmInstance.close();
    }
}
