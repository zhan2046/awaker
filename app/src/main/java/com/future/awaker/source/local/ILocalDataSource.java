package com.future.awaker.source.local;

import com.awaker.annotation.Delegate;
import com.awaker.annotation.SingleDelegate;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by ruzhan
 */

@SingleDelegate(
        classNameImpl = "LocalDataSourceImpl",
        delegate = @Delegate(
                delegatePackage = "com.future.awaker.source",
                delegateClassName = "RealmManager",
                delegateSimpleName = "realmManager"
        ))
public interface ILocalDataSource {

    Flowable<RealmResults> getLocalRealm(Class clazz, HashMap<String, String> map);

    void updateLocalRealm(RealmModel realmModel);

    void deleteLocalRealm(Class clazz, Map<String, String> map);

    void clearAll();

    void close();
}
