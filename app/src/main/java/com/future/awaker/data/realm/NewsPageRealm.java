package com.future.awaker.data.realm;

import com.future.awaker.data.News;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Copyright ©2017 by ruzhan
 */

public class NewsPageRealm extends RealmObject {

    public static final String ID = "news_page_id";

    @PrimaryKey
    private String news_page_id;
    private RealmList<NewsRealm> newsList;

    public static List<News> getNewsList(RealmList<NewsRealm> list) {
        if (list == null) {
            return null;
        }
        List<News> newsList = new ArrayList<>();
        for (NewsRealm item : list) {
            News news = NewsRealm.setNewsRealm(item);
            newsList.add(news);
        }
        return newsList;
    }

    public static RealmList<NewsRealm> getNewsRealmList(List<News> list) {
        if (list == null) {
            return null;
        }
        RealmList<NewsRealm> newsList = new RealmList<>();
        for (News item : list) {
            NewsRealm newsRealm = NewsRealm.setNews(item);
            newsList.add(newsRealm);
        }
        return newsList;
    }

    public String getNews_page_id() {
        return news_page_id;
    }

    public void setNews_page_id(String news_page_id) {
        this.news_page_id = news_page_id;
    }

    public RealmList<NewsRealm> getNewsList() {
        return newsList;
    }

    public void setNewsList(RealmList<NewsRealm> newsList) {
        this.newsList = newsList;
    }
}
