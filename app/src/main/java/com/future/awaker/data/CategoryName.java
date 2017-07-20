package com.future.awaker.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ruzhan on 2017/7/15.
 */

public class CategoryName extends RealmObject {

    @PrimaryKey
    public String id;
    public String title;
    public String pid;
    public String can_post;
    public String need_audit;
    public String sort;
    public String status;
    public String iocn;
    public String color;

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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCan_post() {
        return can_post;
    }

    public void setCan_post(String can_post) {
        this.can_post = can_post;
    }

    public String getNeed_audit() {
        return need_audit;
    }

    public void setNeed_audit(String need_audit) {
        this.need_audit = need_audit;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIocn() {
        return iocn;
    }

    public void setIocn(String iocn) {
        this.iocn = iocn;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
