package com.future.awaker.data.source;

import com.future.awaker.data.News;
import com.future.awaker.data.Special;
import com.future.awaker.data.realm.NewsPageRealm;
import com.future.awaker.data.realm.NewsRealm;
import com.future.awaker.data.realm.SpecialPageRealm;
import com.future.awaker.data.realm.SpecialRealm;
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
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public final class RealmManager {

    private static final String TAG = RealmManager.class.getSimpleName();

    @SuppressWarnings("unchecked")
    public Flowable<RealmResults> getRealmItems(Class clazz,
                                                Map<String, String> map) {
        return Flowable.create(emitter -> {
            Realm realmInstance = Realm.getDefaultInstance();
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

            Realm realmInstance = Realm.getDefaultInstance();
            realmInstance.executeTransactionAsync(realm ->
                    realm.copyToRealmOrUpdate(newsPageRealm));
        }, BackpressureStrategy.LATEST)
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "doOnError: " + throwable.toString()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public void updateLocalSpecialList(String cat, List<Special> specialList) {
        Flowable.create((FlowableOnSubscribe<List<News>>) e -> {
            SpecialPageRealm specialPageRealm = new SpecialPageRealm();
            RealmList<SpecialRealm> realmList =
                    SpecialPageRealm.getSpecialRealmList(specialList);
            specialPageRealm.setCat(cat);
            specialPageRealm.setSpecialList(realmList);

            Realm realmInstance = Realm.getDefaultInstance();
            realmInstance.executeTransactionAsync(realm ->
                    realm.copyToRealmOrUpdate(specialPageRealm));
        }, BackpressureStrategy.LATEST)
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "doOnError: " + throwable.toString()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    @SuppressWarnings("unchecked")
    public void deleteLocalNewList(String newId) {
        Realm realmInstance = Realm.getDefaultInstance();
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

    @SuppressWarnings("unchecked")
    public void deleteLocalSpecialList(String cat) {
        Realm realmInstance = Realm.getDefaultInstance();
        RealmResults results = realmInstance.where(SpecialPageRealm.class)
                .equalTo(SpecialPageRealm.CAT, cat)
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
        Realm.getDefaultInstance().close();
    }
}
