package com.ruzhan.awaker.article.model;


import android.arch.persistence.room.Embedded;

/**
 * Copyright ©2017 by ruzhan
 */

public class UserDetail {

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
    @Embedded(prefix = "user_info_data_level")
    public Level level;
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
}
