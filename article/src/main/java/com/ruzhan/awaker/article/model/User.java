package com.ruzhan.awaker.article.model;

import android.arch.persistence.room.Ignore;

import java.util.List;

public class User {

    public String avatar32;
    public String avatar64;
    public String avatar128;
    public String avatar256;
    public String avatar512;
    public String uid;
    public String nickname;
    public String title;
    public String score1;
    public String real_nickname;
    @Ignore
    public List<RankLink> rank_link;
}
