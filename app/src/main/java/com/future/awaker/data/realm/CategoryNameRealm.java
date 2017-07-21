package com.future.awaker.data.realm;

import com.future.awaker.data.CategoryName;

import io.realm.RealmObject;

/**
 * Copyright ©2017 by Teambition
 */

public class CategoryNameRealm extends RealmObject {

    public String id;
    public String title;
    public String pid;
    public String can_post;
    public String need_audit;
    public String sort;
    public String status;
    public String iocn;
    public String color;

    public static CategoryNameRealm setCategoryName(CategoryName categoryName) {
        if (categoryName == null) {
            return null;
        }
        CategoryNameRealm categoryNameRealm = new CategoryNameRealm();
        categoryNameRealm.id = categoryName.id;
        categoryNameRealm.title = categoryName.title;
        categoryNameRealm.pid = categoryName.pid;
        categoryNameRealm.can_post = categoryName.can_post;
        categoryNameRealm.need_audit = categoryName.need_audit;
        categoryNameRealm.sort = categoryName.sort;
        categoryNameRealm.status = categoryName.status;
        categoryNameRealm.iocn = categoryName.iocn;
        categoryNameRealm.color = categoryName.color;
        return categoryNameRealm;
    }

    public static CategoryName setCategoryNameRealm(CategoryNameRealm categoryNameRealm) {
        if (categoryNameRealm == null) {
            return null;
        }
        CategoryName categoryName = new CategoryName();
        categoryName.id = categoryNameRealm.id;
        categoryName.title = categoryNameRealm.title;
        categoryName.pid = categoryNameRealm.pid;
        categoryName.can_post = categoryNameRealm.can_post;
        categoryName.need_audit = categoryNameRealm.need_audit;
        categoryName.sort = categoryNameRealm.sort;
        categoryName.status = categoryNameRealm.status;
        categoryName.iocn = categoryNameRealm.iocn;
        categoryName.color = categoryNameRealm.color;
        return categoryName;
    }

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
