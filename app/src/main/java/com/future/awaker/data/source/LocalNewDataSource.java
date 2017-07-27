package com.future.awaker.data.source;

import com.future.awaker.data.BannerItem;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.News;
import com.future.awaker.data.Special;
import com.future.awaker.data.realm.BannerRealm;
import com.future.awaker.data.realm.NewDetailRealm;
import com.future.awaker.data.realm.NewsPageRealm;
import com.future.awaker.data.realm.SpecialPageRealm;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public class LocalNewDataSource {

    private RealmManager realmManager = new RealmManager();

    public Flowable<RealmResults> getLocalNewList(HashMap<String, String> map) {
        return Flowable.defer(() -> realmManager.getRealmItems(NewsPageRealm.class, map));
    }

    public Flowable<RealmResults> getLocalSpecialList(HashMap<String, String> map) {
        return Flowable.defer(() -> realmManager.getRealmItems(SpecialPageRealm.class, map));
    }

    public Flowable<RealmResults> getLocalBanner(HashMap<String, String> map) {
        return Flowable.defer(() -> realmManager.getRealmItems(BannerRealm.class, map));
    }

    public Flowable<RealmResults> getLocalNewDetail(HashMap<String, String> map) {
        return Flowable.defer(() -> realmManager.getRealmItems(NewDetailRealm.class, map));
    }

    public void updateLocalNewList(String newId, List<News> newsList) {
        if (newsList == null || newsList.isEmpty()) {
            return;
        }
        realmManager.updateLocalNewList(newId, newsList);
    }

    public void updateLocalSpecialList(String cat, List<Special> specialList) {
        if (specialList == null || specialList.isEmpty()) {
            return;
        }
        realmManager.updateLocalSpecialList(cat, specialList);
    }

    public void updateLocalBanner(String id, List<BannerItem> bannerItemList) {
        if (bannerItemList == null || bannerItemList.isEmpty()) {
            return;
        }
        realmManager.updateLocalBanner(id, bannerItemList);
    }

    public void updateLocalNewDetail(NewDetail newDetail) {
        if (newDetail == null) {
            return;
        }
        realmManager.updateLocalNewDetail(newDetail);
    }

    public void deleteLocalNewList(String newId) {
        realmManager.deleteLocalNewList(newId);
    }

    public void deleteLocalSpecialList(String cat) {
        realmManager.deleteLocalSpecialList(cat);
    }

    public void deleteLocalBanner(String id) {
        realmManager.deleteLocalBanner(id);
    }

    public void deleteLocalNewDetail(String id) {
        realmManager.deleteLocalNewDetail(id);
    }

    public void close() {
        realmManager.close();
    }
}
