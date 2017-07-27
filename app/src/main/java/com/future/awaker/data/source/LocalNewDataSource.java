package com.future.awaker.data.source;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public class LocalNewDataSource {

    private RealmManager realmManager = new RealmManager();

    public Flowable<RealmResults> getLocalRealm(Class clazz, HashMap<String, String> map) {
        return Flowable.defer(() -> realmManager.getRealmItems(clazz, map));
    }

    public void updateLocalRealm(RealmModel realmModel) {
        realmManager.updateLocalRealm(realmModel);
    }

    public void deleteLocalRealm(Class clazz, Map<String, String> map) {
        realmManager.deleteLocalRealm(clazz, map);
    }

    public void close() {
        realmManager.close();
    }
}
