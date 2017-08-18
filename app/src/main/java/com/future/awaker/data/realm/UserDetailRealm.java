package com.future.awaker.data.realm;

import com.future.awaker.data.UserDetail;

import io.realm.RealmObject;

/**
 * Copyright ©2017 by ruzhan
 */

public class UserDetailRealm extends RealmObject {

    public String uid;
    public String nickname;
    public String sex;
    public String signature;
    public String pos_province;
    public String pos_city;
    public String pos_district;
    public String score1;
    public String score2;
    public String score3;
    public String score4;
    public String email;
    public String mobile;
    public String real_nickname;
    public String score;
    public String title;
    public String fans;
    public String following;
    public LevelRealm level;
    public String now_shop_score;
    public int is_self;
    public String message_unread_count;
    public String avatar32;
    public String avatar64;
    public String avatar128;
    public String avatar256;
    public String avatar512;
    public int is_following;
    public int is_followed;

    public static UserDetailRealm getUserDetailRealm(UserDetail userDetail) {
        UserDetailRealm userDetailRealm = new UserDetailRealm();
        if (userDetail != null) {
            userDetailRealm.uid = userDetail.uid;
            userDetailRealm.nickname = userDetail.nickname;
            userDetailRealm.sex = userDetail.sex;
            userDetailRealm.signature = userDetail.signature;
            userDetailRealm.pos_province = userDetail.pos_province;
            userDetailRealm.pos_city = userDetail.pos_city;
            userDetailRealm.pos_district = userDetail.pos_district;
            userDetailRealm.score1 = userDetail.score1;
            userDetailRealm.score2 = userDetail.score2;
            userDetailRealm.score3 = userDetail.score3;
            userDetailRealm.score4 = userDetail.score4;
            userDetailRealm.email = userDetail.email;
            userDetailRealm.mobile = userDetail.mobile;
            userDetailRealm.real_nickname = userDetail.real_nickname;
            userDetailRealm.score = userDetail.score;
            userDetailRealm.title = userDetail.title;
            userDetailRealm.fans = userDetail.fans;
            userDetailRealm.following = userDetail.following;
            userDetailRealm.level = LevelRealm.getLevelRealm(userDetail.level);
            userDetailRealm.now_shop_score = userDetail.now_shop_score;
            userDetailRealm.is_self = userDetail.is_self;
            userDetailRealm.message_unread_count = userDetail.message_unread_count;
            userDetailRealm.avatar32 = userDetail.avatar32;
            userDetailRealm.avatar64 = userDetail.avatar64;
            userDetailRealm.avatar128 = userDetail.avatar128;
            userDetailRealm.avatar256 = userDetail.avatar256;
            userDetailRealm.avatar512 = userDetail.avatar512;
            userDetailRealm.is_following = userDetail.is_following;
            userDetailRealm.is_followed = userDetail.is_followed;
        }
        return userDetailRealm;
    }

    public static UserDetail getUserDetail(UserDetailRealm userDetailRealm) {
        UserDetail userDetail = new UserDetail();
        if (userDetailRealm != null) {
            userDetail.uid = userDetailRealm.uid;
            userDetail.nickname = userDetailRealm.nickname;
            userDetail.sex = userDetailRealm.sex;
            userDetail.signature = userDetailRealm.signature;
            userDetail.pos_province = userDetailRealm.pos_province;
            userDetail.pos_city = userDetailRealm.pos_city;
            userDetail.pos_district = userDetailRealm.pos_district;
            userDetail.score1 = userDetailRealm.score1;
            userDetail.score2 = userDetailRealm.score2;
            userDetail.score3 = userDetailRealm.score3;
            userDetail.score4 = userDetailRealm.score4;
            userDetail.email = userDetailRealm.email;
            userDetail.mobile = userDetailRealm.mobile;
            userDetail.real_nickname = userDetailRealm.real_nickname;
            userDetail.score = userDetailRealm.score;
            userDetail.title = userDetailRealm.title;
            userDetail.fans = userDetailRealm.fans;
            userDetail.following = userDetailRealm.following;
            userDetail.level = LevelRealm.getLevel(userDetailRealm.level);
            userDetail.now_shop_score = userDetailRealm.now_shop_score;
            userDetail.is_self = userDetailRealm.is_self;
            userDetail.message_unread_count = userDetailRealm.message_unread_count;
            userDetail.avatar32 = userDetailRealm.avatar32;
            userDetail.avatar64 = userDetailRealm.avatar64;
            userDetail.avatar128 = userDetailRealm.avatar128;
            userDetail.avatar256 = userDetailRealm.avatar256;
            userDetail.avatar512 = userDetailRealm.avatar512;
            userDetail.is_following = userDetailRealm.is_following;
            userDetail.is_followed = userDetailRealm.is_followed;
        }
        return userDetail;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPos_province() {
        return pos_province;
    }

    public void setPos_province(String pos_province) {
        this.pos_province = pos_province;
    }

    public String getPos_city() {
        return pos_city;
    }

    public void setPos_city(String pos_city) {
        this.pos_city = pos_city;
    }

    public String getPos_district() {
        return pos_district;
    }

    public void setPos_district(String pos_district) {
        this.pos_district = pos_district;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    public String getScore3() {
        return score3;
    }

    public void setScore3(String score3) {
        this.score3 = score3;
    }

    public String getScore4() {
        return score4;
    }

    public void setScore4(String score4) {
        this.score4 = score4;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReal_nickname() {
        return real_nickname;
    }

    public void setReal_nickname(String real_nickname) {
        this.real_nickname = real_nickname;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public LevelRealm getLevel() {
        return level;
    }

    public void setLevel(LevelRealm level) {
        this.level = level;
    }

    public String getNow_shop_score() {
        return now_shop_score;
    }

    public void setNow_shop_score(String now_shop_score) {
        this.now_shop_score = now_shop_score;
    }

    public int getIs_self() {
        return is_self;
    }

    public void setIs_self(int is_self) {
        this.is_self = is_self;
    }

    public String getMessage_unread_count() {
        return message_unread_count;
    }

    public void setMessage_unread_count(String message_unread_count) {
        this.message_unread_count = message_unread_count;
    }

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

    public int getIs_following() {
        return is_following;
    }

    public void setIs_following(int is_following) {
        this.is_following = is_following;
    }

    public int getIs_followed() {
        return is_followed;
    }

    public void setIs_followed(int is_followed) {
        this.is_followed = is_followed;
    }
}
