package com.ruzhan.awaker.article.model;


import androidx.room.Embedded;

public class Comment {

    public static final String NEW_DETAIL = "new_detail";
    public static final String NICE_COMMENT = "nice_comment";

    public String id;
    public String uid;
    public String app;
    public String mod;
    public String row_id;
    public String parse;
    public String content;
    public String create_time;
    public String pid;
    public String status;
    public String ip;
    public String area;
    public String up;
    public String down;
    public String type;
    public String sina_name;
    public String sina_avatar;
    public String sina_url;
    @Embedded(prefix = "user")
    public User user;
    @Embedded(prefix = "newstitle")
    public CommentTitle newstitle;
    public boolean isSelect;
}
