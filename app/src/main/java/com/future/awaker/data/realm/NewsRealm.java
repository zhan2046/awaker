package com.future.awaker.data.realm;

import com.future.awaker.data.News;

import io.realm.RealmObject;

/**
 * Copyright ©2017 by ruzhan
 */

public class NewsRealm extends RealmObject {

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
    public CoverUrlRealm cover_url;
    public String approval;
    public String share_url;
    public UserRealm user;
    public String category_title;
    public String category_color;
    public CategoryNameRealm category_name;

    public static NewsRealm setNews(News news) {
        if (news == null) {
            return null;
        }
        NewsRealm newsRealm = new NewsRealm();
        newsRealm.id = news.id;
        newsRealm.uid = news.uid;
        newsRealm.title = news.title;
        newsRealm.description = news.description;
        newsRealm.category = news.category;
        newsRealm.status = news.status;
        newsRealm.reason = news.reason;
        newsRealm.sort = news.sort;
        newsRealm.position = news.position;
        newsRealm.cover = news.cover;
        newsRealm.view = news.view;
        newsRealm.comment = news.comment;
        newsRealm.collection = news.collection;
        newsRealm.dead_line = news.dead_line;
        newsRealm.source = news.source;
        newsRealm.create_time = news.create_time;
        newsRealm.update_time = news.update_time;
        newsRealm.approval = news.approval;
        newsRealm.share_url = news.share_url;
        newsRealm.category_title = news.category_title;
        newsRealm.category_color = news.category_color;

        newsRealm.cover_url = CoverUrlRealm.setCoverUrl(news.cover_url);
        newsRealm.user = UserRealm.setUser(news.user);
        newsRealm.category_name =
                CategoryNameRealm.setCategoryName(news.category_name);

        return newsRealm;
    }

    public static News setNewsRealm(NewsRealm newsRealm) {
        if (newsRealm == null) {
            return null;
        }
        News news = new News();
        news.id = newsRealm.id;
        news.uid = newsRealm.uid;
        news.title = newsRealm.title;
        news.description = newsRealm.description;
        news.category = newsRealm.category;
        news.status = newsRealm.status;
        news.reason = newsRealm.reason;
        news.sort = newsRealm.sort;
        news.position = newsRealm.position;
        news.cover = newsRealm.cover;
        news.view = newsRealm.view;
        news.comment = newsRealm.comment;
        news.collection = newsRealm.collection;
        news.dead_line = newsRealm.dead_line;
        news.source = newsRealm.source;
        news.create_time = newsRealm.create_time;
        news.update_time = newsRealm.update_time;
        news.approval = newsRealm.approval;
        news.share_url = newsRealm.share_url;
        news.category_title = newsRealm.category_title;
        news.category_color = newsRealm.category_color;

        news.cover_url = CoverUrlRealm.setCoverUrlRealm(newsRealm.cover_url);
        news.user = UserRealm.setUserRealm(newsRealm.user);
        news.category_name =
                CategoryNameRealm.setCategoryNameRealm(newsRealm.category_name);

        return news;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getDead_line() {
        return dead_line;
    }

    public void setDead_line(String dead_line) {
        this.dead_line = dead_line;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public CoverUrlRealm getCover_url() {
        return cover_url;
    }

    public void setCover_url(CoverUrlRealm cover_url) {
        this.cover_url = cover_url;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public UserRealm getUser() {
        return user;
    }

    public void setUser(UserRealm user) {
        this.user = user;
    }

    public String getCategory_title() {
        return category_title;
    }

    public void setCategory_title(String category_title) {
        this.category_title = category_title;
    }

    public String getCategory_color() {
        return category_color;
    }

    public void setCategory_color(String category_color) {
        this.category_color = category_color;
    }

    public CategoryNameRealm getCategory_name() {
        return category_name;
    }

    public void setCategory_name(CategoryNameRealm category_name) {
        this.category_name = category_name;
    }
}
