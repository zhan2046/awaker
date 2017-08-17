package com.future.awaker.data.realm;

import com.future.awaker.data.BannerItem;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ruzhan on 2017/7/22.
 */

public class BannerRealm extends RealmObject {

    public static final String ID = "banner_id";
    public static final String ID_VALUE = "0";

    @PrimaryKey
    public String banner_id = ID_VALUE;
    public RealmList<BannerItemRealm> bannerItemList;

    public static List<BannerItem> getBannerItemList(RealmList<BannerItemRealm> itemRealmList) {
        if (itemRealmList == null || itemRealmList.isEmpty()) {
            return null;
        }
        List<BannerItem> bannerItemList = new ArrayList<>();
        for (BannerItemRealm itemRealm : itemRealmList) {
            BannerItem bannerItem = BannerItemRealm.setBannerItemRealm(itemRealm);
            bannerItemList.add(bannerItem);
        }
        return bannerItemList;
    }

    public static RealmList<BannerItemRealm> getBannerItemRealmList(List<BannerItem> itemList) {
        if (itemList == null || itemList.isEmpty()) {
            return null;
        }
        RealmList<BannerItemRealm> itemRealmList = new RealmList<>();
        for (BannerItem bannerItem : itemList) {
            BannerItemRealm bannerItemRealm = BannerItemRealm.setBannerItem(bannerItem);
            itemRealmList.add(bannerItemRealm);
        }
        return itemRealmList;
    }

    public RealmList<BannerItemRealm> getBannerItemList() {
        return bannerItemList;
    }

    public void setBannerItemList(RealmList<BannerItemRealm> bannerItemList) {
        this.bannerItemList = bannerItemList;
    }

    public String getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(String banner_id) {
        this.banner_id = banner_id;
    }
}
