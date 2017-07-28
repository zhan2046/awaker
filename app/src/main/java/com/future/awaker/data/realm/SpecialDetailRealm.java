package com.future.awaker.data.realm;

import com.future.awaker.data.SpecialDetail;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ruzhan on 2017/7/15.
 */

public class SpecialDetailRealm extends RealmObject {

    public static final String ID = "special_detail_id";

    @PrimaryKey
    public String special_detail_id;
    public String id;
    public String title;
    public String content;
    public String cover;
    public RealmList<NewsRealm> newslist;

    public static SpecialDetail setSpecialDetailRealm(SpecialDetailRealm realm) {
        if (realm == null) {
            return null;
        }
        SpecialDetail specialDetail = new SpecialDetail();
        specialDetail.id = realm.id;
        specialDetail.title = realm.title;
        specialDetail.content = realm.content;
        specialDetail.cover = realm.cover;
        specialDetail.newslist = NewsPageRealm.getNewsList(realm.newslist);
        return specialDetail;
    }

    public static SpecialDetailRealm setSpecialDetail(SpecialDetail specialDetail) {
        if (specialDetail == null) {
            return null;
        }
        SpecialDetailRealm realm = new SpecialDetailRealm();
        realm.id = specialDetail.id;
        realm.special_detail_id = specialDetail.id;
        realm.title = specialDetail.title;
        realm.content = specialDetail.content;
        realm.cover = specialDetail.cover;
        realm.newslist = NewsPageRealm.getNewsRealmList(specialDetail.newslist);
        return realm;
    }

    public static String getID() {
        return ID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.special_detail_id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public RealmList<NewsRealm> getNewslist() {
        return newslist;
    }

    public void setNewslist(RealmList<NewsRealm> newslist) {
        this.newslist = newslist;
    }
}
