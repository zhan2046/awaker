package com.future.awaker.data.source.local;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public interface ILocalNewDataSource {

    Flowable<RealmResults> getLocalRealm(Class clazz, HashMap<String, String> map);

    void updateLocalRealm(RealmModel realmModel);

    void deleteLocalRealm(Class clazz, Map<String, String> map);

    void close();
}
