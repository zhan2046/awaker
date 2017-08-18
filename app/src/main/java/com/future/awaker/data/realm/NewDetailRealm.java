package com.future.awaker.data.realm;

import com.future.awaker.data.NewDetail;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Copyright ©2017 by ruzhan
 */

public class NewDetailRealm extends RealmObject {

    public static final String ID = "new_detail_id";
    public static final String DETAIL = "detail";

    @PrimaryKey
    public String new_detail_id;
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
    public String content;

    public static NewDetail setNewDetailRealm(NewDetailRealm realm) {
        if (realm == null) {
            return null;
        }
        NewDetail newDetail = new NewDetail();
        newDetail.id = realm.id;
        newDetail.uid = realm.uid;
        newDetail.title = realm.title;
        newDetail.description = realm.description;
        newDetail.category = realm.category;
        newDetail.status = realm.status;
        newDetail.reason = realm.reason;
        newDetail.sort = realm.sort;
        newDetail.position = realm.position;
        newDetail.cover = realm.cover;
        newDetail.view = realm.view;
        newDetail.comment = realm.comment;
        newDetail.collection = realm.collection;
        newDetail.dead_line = realm.dead_line;
        newDetail.source = realm.source;
        newDetail.create_time = realm.create_time;
        newDetail.update_time = realm.update_time;
        newDetail.cover_url = CoverUrlRealm.setCoverUrlRealm(realm.cover_url);
        newDetail.approval = realm.approval;
        newDetail.share_url = realm.share_url;
        newDetail.user = UserRealm.setUserRealm(realm.user);
        newDetail.category_title = realm.category_title;
        newDetail.category_color = realm.category_color;
        newDetail.content = realm.content;
        return newDetail;
    }

    public static NewDetailRealm setNewDetail(NewDetail newDetail) {
        if (newDetail == null) {
            return null;
        }
        NewDetailRealm realm = new NewDetailRealm();
        realm.id = newDetail.id;
        realm.uid = newDetail.uid;
        realm.title = newDetail.title;
        realm.description = newDetail.description;
        realm.category = newDetail.category;
        realm.status = newDetail.status;
        realm.reason = newDetail.reason;
        realm.sort = newDetail.sort;
        realm.position = newDetail.position;
        realm.cover = newDetail.cover;
        realm.view = newDetail.view;
        realm.comment = newDetail.comment;
        realm.collection = newDetail.collection;
        realm.dead_line = newDetail.dead_line;
        realm.source = newDetail.source;
        realm.create_time = newDetail.create_time;
        realm.update_time = newDetail.update_time;
        realm.cover_url = CoverUrlRealm.setCoverUrl(newDetail.cover_url);
        realm.approval = newDetail.approval;
        realm.share_url = newDetail.share_url;
        realm.user = UserRealm.setUser(newDetail.user);
        realm.category_title = newDetail.category_title;
        realm.category_color = newDetail.category_color;
        realm.content = newDetail.content;
        realm.new_detail_id = newDetail.id + DETAIL;
        return realm;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
