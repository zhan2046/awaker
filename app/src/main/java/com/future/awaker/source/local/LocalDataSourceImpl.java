package com.future.awaker.source.local;

import com.future.awaker.source.RealmManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public class LocalDataSourceImpl implements ILocalDataSource {

    private RealmManager realmManager;

    public LocalDataSourceImpl(RealmManager realmManager) {
        this.realmManager = realmManager;
    }

    @Override
    public Flowable<RealmResults> getLocalRealm(Class clazz, HashMap<String, String> map) {
        return realmManager.getRealmItems(clazz, map);
    }

    @Override
    public void updateLocalRealm(RealmModel realmModel) {
        realmManager.updateLocalRealm(realmModel);
    }

    @Override
    public void deleteLocalRealm(Class clazz, Map<String, String> map) {
        realmManager.deleteLocalRealm(clazz, map);
    }

    @Override
    public void clearAll() {
        realmManager.clearAll();
    }

    @Override
    public void close() {
        realmManager.close();
    }
}
