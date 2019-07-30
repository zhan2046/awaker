package com.ruzhan.awaker.article.model;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "new_detail")
public class NewDetail {

    @NonNull
    @PrimaryKey
    public String id;
    public String uid;
    public String title;
    public String description;
    public String category;
    public String status;
    public String reason;
    public String sort;
    public String position;
    public String cover;
    public String view;
    public String comment;
    public String collection;
    public String dead_line;
    public String source;
    public String create_time;
    public String update_time;
    @Embedded(prefix = "cover_url")
    public CoverUrl cover_url;
    public String approval;
    public String share_url;
    @Embedded(prefix = "user")
    public User user;
    public String category_title;
    public String category_color;
    public String content;

    @Ignore
    public NewDetail() {
    }

    public NewDetail(@NonNull String id, String uid, String title, String description,
                     String category, String status, String reason, String sort, String position,
                     String cover, String view, String comment, String collection, String dead_line,
                     String source, String create_time, String update_time, CoverUrl cover_url,
                     String approval, String share_url, User user, String category_title,
                     String category_color, String content) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.description = description;
        this.category = category;
        this.status = status;
        this.reason = reason;
        this.sort = sort;
        this.position = position;
        this.cover = cover;
        this.view = view;
        this.comment = comment;
        this.collection = collection;
        this.dead_line = dead_line;
        this.source = source;
        this.create_time = create_time;
        this.update_time = update_time;
        this.cover_url = cover_url;
        this.approval = approval;
        this.share_url = share_url;
        this.user = user;
        this.category_title = category_title;
        this.category_color = category_color;
        this.content = content;
    }
}
