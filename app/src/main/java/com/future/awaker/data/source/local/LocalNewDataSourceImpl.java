package com.future.awaker.data.source.local;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.future.awaker.Application;
import com.future.awaker.data.NewListRealm;
import com.future.awaker.data.News;
import com.future.awaker.data.source.callback.NewCallBack;

import java.lang.ref.WeakReference;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

/**
 * Copyright ©2017 by Teambition
 */

public class LocalNewDataSourceImpl implements LocalNewDataSource {

    private static final String AWAKER_DB = "awakerDB";
    private static final int VERSION_CODE = 0;

    private Realm realmInstance;
    private Handler handler = new Handler(Looper.getMainLooper());

    public LocalNewDataSourceImpl() {
        Realm.init(Application.get());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(AWAKER_DB)
                .schemaVersion(VERSION_CODE)
                .build();
        realmInstance = Realm.getInstance(config);
    }

    @Override
    public void getLocalNewList(int newId, NewCallBack newCallBack) {
        WeakReference<NewCallBack> callBackRef = new WeakReference<>(newCallBack);
        realmInstance.where(NewListRealm.class)
                .equalTo("id", newId).findAllAsync()
                .addChangeListener((newBeen, changeSet) -> {
            if (newBeen.isLoaded() && !newBeen.isEmpty()) {
                NewListRealm newListRealm = newBeen.get(0);
                NewCallBack callBack = callBackRef.get();
                if (newListRealm != null && callBack != null) {
                    handler.post(() ->
                            callBack.onNewListSuc(newListRealm.getNewsList()));
                    Log.i("getLocalNewList", "ok !!!");
                }
            }
        });
    }

    @Override
    public void deleteLocalNewList(int newId) {
        realmInstance.where(NewListRealm.class)
                .equalTo("id", newId)
                .findAllAsync()
                .addChangeListener((newBeen, changeSet) -> {
                    if (newBeen.isLoaded()) {
                        newBeen.deleteAllFromRealm();
                    }
                });
    }

    @Override
    public void updateLocalNewList(int newId, List<News> newsList) {
        realmInstance.executeTransactionAsync(realm -> {
            NewListRealm newListRealm = realm.createObject(NewListRealm.class);
            RealmList<News> list = new RealmList<>();
            list.addAll(newsList);
            newListRealm.setNewsList(list);

        }, () -> {
            Log.i("updateLocalNewList", "ok !!!");

        }, error -> {
            Log.i("updateLocalNewList", "error ............");
        });
    }

    public void onDestroy() {
        realmInstance.close();
    }

}
