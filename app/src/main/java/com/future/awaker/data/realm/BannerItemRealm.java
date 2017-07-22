package com.future.awaker.data.realm;

import com.future.awaker.data.BannerItem;

import io.realm.RealmObject;

/**
 * Created by ruzhan on 2017/7/22.
 */

public class BannerItemRealm extends RealmObject {

    public String id;
    public String title;
    public String content;
    public String img_url;
    public String out_url;
    public String newsid;
    public String adv_type;
    public String status;
    public String sort;

    public static BannerItem setBannerItemRealm(BannerItemRealm itemRealm) {
        if (itemRealm == null) {
            return null;
        }
        BannerItem bannerItem = new BannerItem();
        bannerItem.id = itemRealm.id;
        bannerItem.title = itemRealm.title;
        bannerItem.content = itemRealm.content;
        bannerItem.img_url = itemRealm.img_url;
        bannerItem.out_url = itemRealm.out_url;
        bannerItem.newsid = itemRealm.newsid;
        bannerItem.adv_type = itemRealm.adv_type;
        bannerItem.status = itemRealm.status;
        bannerItem.sort = itemRealm.sort;
        return bannerItem;
    }

    public static BannerItemRealm setBannerItem(BannerItem bannerItem) {
        if (bannerItem == null) {
            return null;
        }
        BannerItemRealm itemRealm = new BannerItemRealm();
        itemRealm.id = bannerItem.id;
        itemRealm.title = bannerItem.title;
        itemRealm.content = bannerItem.content;
        itemRealm.img_url = bannerItem.img_url;
        itemRealm.out_url = bannerItem.out_url;
        itemRealm.newsid = bannerItem.newsid;
        itemRealm.adv_type = bannerItem.adv_type;
        itemRealm.status = bannerItem.status;
        itemRealm.sort = bannerItem.sort;
        return itemRealm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getOut_url() {
        return out_url;
    }

    public void setOut_url(String out_url) {
        this.out_url = out_url;
    }

    public String getNewsid() {
        return newsid;
    }

    public void setNewsid(String newsid) {
        this.newsid = newsid;
    }

    public String getAdv_type() {
        return adv_type;
    }

    public void setAdv_type(String adv_type) {
        this.adv_type = adv_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
