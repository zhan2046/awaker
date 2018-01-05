package com.future.awaker.db.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.future.awaker.data.News;

import java.util.ArrayList;
import java.util.List;


@Entity(tableName = "news_entity")
public class NewsEntity {

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
    public CoverUrlEntity cover_url;
    public String approval;
    public String share_url;
    @Embedded(prefix = "user")
    public UserEntity user;
    public String category_title;
    public String category_color;
    @Embedded(prefix = "category_name")
    private CategoryNameEntity category_name;

    public NewsEntity(String id, String uid, String title, String description, String category, String status, String reason, String sort, String position, String cover, String view, String comment, String collection, String dead_line, String source, String create_time, String update_time, CoverUrlEntity cover_url, String approval, String share_url, UserEntity user, String category_title, String category_color, CategoryNameEntity category_name) {
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
        this.category_name = category_name;
    }

    public NewsEntity(News news) {
        this.id = news.id;
        this.uid = news.uid;
        this.title = news.title;
        this.description = news.description;
        this.category = news.category;
        this.status = news.status;
        this.reason = news.reason;
        this.sort = news.sort;
        this.position = news.position;
        this.cover = news.cover;
        this.view = news.view;
        this.comment = news.comment;
        this.collection = news.collection;
        this.dead_line = news.dead_line;
        this.source = news.source;
        this.create_time = news.create_time;
        this.update_time = news.update_time;
        this.cover_url = CoverUrlEntity.setCoverUrl(news.cover_url);
        this.approval = news.approval;
        this.share_url = news.share_url;
        this.user = UserEntity.setUser(news.user);
        this.category_title = news.category_title;
        this.category_color = news.category_color;
        this.category_name = CategoryNameEntity.setCategoryName(news.category_name);
    }

    public static News setNewsEntity(NewsEntity newsEntity) {
        if (newsEntity == null) {
            return null;
        }
        News news = new News();
        news.id = newsEntity.id;
        news.uid = newsEntity.uid;
        news.title = newsEntity.title;
        news.description = newsEntity.description;
        news.category = newsEntity.category;
        news.status = newsEntity.status;
        news.reason = newsEntity.reason;
        news.sort = newsEntity.sort;
        news.position = newsEntity.position;
        news.cover = newsEntity.cover;
        news.view = newsEntity.view;
        news.comment = newsEntity.comment;
        news.collection = newsEntity.collection;
        news.dead_line = newsEntity.dead_line;
        news.source = newsEntity.source;
        news.create_time = newsEntity.create_time;
        news.update_time = newsEntity.update_time;
        news.approval = newsEntity.approval;
        news.share_url = newsEntity.share_url;
        news.category_title = newsEntity.category_title;
        news.category_color = newsEntity.category_color;

        news.cover_url = CoverUrlEntity.setCoverUrlEntity(newsEntity.cover_url);
        news.user = UserEntity.setUserEntity(newsEntity.user);
        news.category_name =
                CategoryNameEntity.setCategoryNameEntity(newsEntity.category_name);

        return news;
    }

    public static List<News> getNewsList(List<NewsEntity> list) {
        if (list == null) {
            return null;
        }
        List<News> newsList = new ArrayList<>();
        for (NewsEntity item : list) {
            News news = NewsEntity.setNewsEntity(item);
            newsList.add(news);
        }
        return newsList;
    }

    public static List<NewsEntity> getNewsEntityList(List<News> list) {
        if (list == null) {
            return null;
        }
        List<NewsEntity> newsList = new ArrayList<>();
        for (News item : list) {
            NewsEntity newsEntity = new NewsEntity(item);
            newsList.add(newsEntity);
        }
        return newsList;
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

    public CoverUrlEntity getCover_url() {
        return cover_url;
    }

    public void setCover_url(CoverUrlEntity cover_url) {
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

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
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

    public CategoryNameEntity getCategory_name() {
        return category_name;
    }

    public void setCategory_name(CategoryNameEntity category_name) {
        this.category_name = category_name;
    }
}
