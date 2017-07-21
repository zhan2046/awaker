package com.future.awaker.data.realm;

import com.future.awaker.data.Special;

import io.realm.RealmObject;

/**
 * Created by ruzhan on 2017/7/21.
 */

public class SpecialRealm extends RealmObject {

    public String id;
    public String title;
    public String cover_id;
    public String special_id;
    public String create_time;
    public String cover;
    public int position;

    public static SpecialRealm setSpecial(Special special) {
        if (special == null) {
            return null;
        }
        SpecialRealm specialRealm = new SpecialRealm();
        specialRealm.id = special.id;
        specialRealm.title = special.title;
        specialRealm.cover_id = special.cover_id;
        specialRealm.special_id = special.special_id;
        specialRealm.create_time = special.create_time;
        specialRealm.cover = special.cover;
        specialRealm.position = special.position;
        return specialRealm;
    }

    public static Special setSpecialRealm(SpecialRealm specialRealm) {
        if (specialRealm == null) {
            return null;
        }
        Special special = new Special();
        special.id = specialRealm.id;
        special.title = specialRealm.title;
        special.cover_id = specialRealm.cover_id;
        special.special_id = specialRealm.special_id;
        special.create_time = specialRealm.create_time;
        special.cover = specialRealm.cover;
        special.position = specialRealm.position;
        return special;
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

    public String getCover_id() {
        return cover_id;
    }

    public void setCover_id(String cover_id) {
        this.cover_id = cover_id;
    }

    public String getSpecial_id() {
        return special_id;
    }

    public void setSpecial_id(String special_id) {
        this.special_id = special_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
