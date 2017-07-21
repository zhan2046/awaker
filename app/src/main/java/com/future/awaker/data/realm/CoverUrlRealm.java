package com.future.awaker.data.realm;

import com.future.awaker.data.CoverUrl;

import io.realm.RealmObject;

/**
 * Copyright ©2017 by Teambition
 */

public class CoverUrlRealm extends RealmObject {

    public String ori;
    public String thumb;
    public String banana;

    public static CoverUrlRealm setCoverUrl(CoverUrl coverUrl) {
        if (coverUrl == null) {
            return null;
        }
        CoverUrlRealm coverUrlRealm = new CoverUrlRealm();
        coverUrlRealm.ori = coverUrl.ori;
        coverUrlRealm.thumb = coverUrl.thumb;
        coverUrlRealm.banana = coverUrl.banana;
        return coverUrlRealm;
    }

    public static CoverUrl setCoverUrlRealm(CoverUrlRealm coverUrlRealm) {
        if (coverUrlRealm == null) {
            return null;
        }
        CoverUrl coverUrl = new CoverUrl();
        coverUrl.ori = coverUrlRealm.ori;
        coverUrl.thumb = coverUrlRealm.thumb;
        coverUrl.banana = coverUrlRealm.banana;
        return coverUrl;
    }

    public String getOri() {
        return ori;
    }

    public void setOri(String ori) {
        this.ori = ori;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getBanana() {
        return banana;
    }

    public void setBanana(String banana) {
        this.banana = banana;
    }
}
