package com.future.awaker.data.realm;

import com.future.awaker.data.Special;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ruzhan on 2017/7/21.
 */

public class SpecialPageRealm extends RealmObject {

    public static final String CAT = "cat";

    @PrimaryKey
    private String cat;
    private RealmList<SpecialRealm> specialList;

    public static List<Special> getSpecialList(RealmList<SpecialRealm> list) {
        if (list == null) {
            return null;
        }
        List<Special> newsList = new ArrayList<>();
        for (SpecialRealm item : list) {
            Special special = SpecialRealm.setSpecialRealm(item);
            newsList.add(special);
        }
        return newsList;
    }

    public static RealmList<SpecialRealm> getSpecialRealmList(List<Special> list) {
        if (list == null) {
            return null;
        }
        RealmList<SpecialRealm> newsList = new RealmList<>();
        for (Special item : list) {
            SpecialRealm specialRealm = SpecialRealm.setSpecial(item);
            newsList.add(specialRealm);
        }
        return newsList;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public RealmList<SpecialRealm> getSpecialList() {
        return specialList;
    }

    public void setSpecialList(RealmList<SpecialRealm> specialList) {
        this.specialList = specialList;
    }
}
