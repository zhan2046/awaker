package com.future.awaker.data.source.local;

import com.future.awaker.data.News;
import com.future.awaker.data.realm.NewsPageRealm;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public class LocalNewDataSource implements ILocalNewDataSource {

    private RealmManager realmManager = new RealmManager();

    @Override
    public Flowable<RealmResults> getLocalNewList(HashMap<String, String> map) {
        return Flowable.defer(() -> realmManager.getRealmItems(NewsPageRealm.class, map));
    }

    @Override
    public void deleteLocalNewList(String newId) {
        realmManager.deleteLocalNewList(newId);
    }

    @Override
    public void updateLocalNewList(String newId, List<News> newsList) {
        realmManager.updateLocalNewList(newId, newsList);
    }

    @Override
    public void close() {
        realmManager.close();
    }
}
