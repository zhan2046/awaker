package com.future.awaker.data;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Copyright ©2017 by Teambition
 */

public class User extends RealmObject {

    public String avatar32;
    public String avatar64;
    public String avatar128;
    public String avatar256;
    public String avatar512;
    public String uid;
    @PrimaryKey
    public String nickname;
    public String title;
    public String score1;
    public String real_nickname;
    public RealmList<RankLink> rank_link;

    public String getAvatar32() {
        return avatar32;
    }

    public void setAvatar32(String avatar32) {
        this.avatar32 = avatar32;
    }

    public String getAvatar64() {
        return avatar64;
    }

    public void setAvatar64(String avatar64) {
        this.avatar64 = avatar64;
    }

    public String getAvatar128() {
        return avatar128;
    }

    public void setAvatar128(String avatar128) {
        this.avatar128 = avatar128;
    }

    public String getAvatar256() {
        return avatar256;
    }

    public void setAvatar256(String avatar256) {
        this.avatar256 = avatar256;
    }

    public String getAvatar512() {
        return avatar512;
    }

    public void setAvatar512(String avatar512) {
        this.avatar512 = avatar512;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getReal_nickname() {
        return real_nickname;
    }

    public void setReal_nickname(String real_nickname) {
        this.real_nickname = real_nickname;
    }

    public RealmList<RankLink> getRank_link() {
        return rank_link;
    }

    public void setRank_link(RealmList<RankLink> rank_link) {
        this.rank_link = rank_link;
    }
}
