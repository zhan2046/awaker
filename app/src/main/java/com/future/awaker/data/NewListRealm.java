package com.future.awaker.data;

import com.future.awaker.data.News;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Copyright ©2017 by Teambition
 */

public class NewListRealm extends RealmObject {

    private static final int ID = 0;

    private int id = ID;
    private RealmList<News> newsList;

    public RealmList<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(RealmList<News> newsList) {
        this.newsList = newsList;
    }

    public static int getID() {
        return ID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
