package com.future.awaker.data.source.local;

import com.future.awaker.data.News;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public interface LocalNewDataSource {

    <T extends RealmModel> Flowable<RealmResults<T>> getLocalNewList(HashMap<String, String> map);

    void deleteLocalNewList(String newId);

    void updateLocalNewList(String newId, List<News> newsList);
}
