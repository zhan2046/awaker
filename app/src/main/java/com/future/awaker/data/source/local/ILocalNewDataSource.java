package com.future.awaker.data.source.local;

import com.future.awaker.data.News;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public interface ILocalNewDataSource {

    Flowable<RealmResults> getLocalNewList(HashMap<String, String> map);

    void deleteLocalNewList(String newId);

    void updateLocalNewList(String newId, List<News> newsList);

    void close();
}
