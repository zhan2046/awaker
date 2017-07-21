package com.future.awaker.data.source;

import com.future.awaker.data.News;
import com.future.awaker.data.Special;
import com.future.awaker.data.realm.NewsPageRealm;
import com.future.awaker.data.realm.SpecialPageRealm;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public class LocalNewDataSource {

    private RealmManager realmManager = new RealmManager();

    public Flowable<RealmResults> getLocalNewList(HashMap<String, String> map) {
        return Flowable.defer(() -> realmManager.getRealmItems(NewsPageRealm.class, map));
    }

    public Flowable<RealmResults> getLocalSpecialList(HashMap<String, String> map) {
        return Flowable.defer(() -> realmManager.getRealmItems(SpecialPageRealm.class, map));
    }

    public void updateLocalNewList(String newId, List<News> newsList) {
        realmManager.updateLocalNewList(newId, newsList);
    }

    public void updateLocalSpecialList(String cat, List<Special> specialList) {
        realmManager.updateLocalSpecialList(cat, specialList);
    }

    public void deleteLocalNewList(String newId) {
        realmManager.deleteLocalNewList(newId);
    }

    public void deleteLocalSpecialList(String cat) {
        realmManager.deleteLocalSpecialList(cat);
    }

    public void close() {
        realmManager.close();
    }
}
