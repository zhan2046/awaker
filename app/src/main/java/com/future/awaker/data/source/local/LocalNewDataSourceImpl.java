package com.future.awaker.data.source.local;

import com.future.awaker.data.source.RealmManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public class LocalNewDataSourceImpl implements ILocalNewDataSource {

    private RealmManager realmManager = new RealmManager();

    @Override
    public Flowable<RealmResults> getLocalRealm(Class clazz, HashMap<String, String> map) {
        return Flowable.defer(() -> realmManager.getRealmItems(clazz, map));
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
    public void close() {
        realmManager.close();
    }
}
