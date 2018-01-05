package com.future.awaker.db.entity;

import com.future.awaker.data.CategoryName;


public class CategoryNameEntity {

    public String id;
    public String title;
    public String pid;
    public String can_post;
    public String need_audit;
    public String sort;
    public String status;
    public String iocn;
    public String color;

    public static CategoryNameEntity setCategoryName(CategoryName categoryName) {
        if (categoryName == null) {
            return null;
        }
        CategoryNameEntity categoryNameEntity = new CategoryNameEntity();
        categoryNameEntity.id = categoryName.id;
        categoryNameEntity.title = categoryName.title;
        categoryNameEntity.pid = categoryName.pid;
        categoryNameEntity.can_post = categoryName.can_post;
        categoryNameEntity.need_audit = categoryName.need_audit;
        categoryNameEntity.sort = categoryName.sort;
        categoryNameEntity.status = categoryName.status;
        categoryNameEntity.iocn = categoryName.iocn;
        categoryNameEntity.color = categoryName.color;
        return categoryNameEntity;
    }

    public static CategoryName setCategoryNameEntity(CategoryNameEntity categoryNameEntity) {
        if (categoryNameEntity == null) {
            return null;
        }
        CategoryName categoryName = new CategoryName();
        categoryName.id = categoryNameEntity.id;
        categoryName.title = categoryNameEntity.title;
        categoryName.pid = categoryNameEntity.pid;
        categoryName.can_post = categoryNameEntity.can_post;
        categoryName.need_audit = categoryNameEntity.need_audit;
        categoryName.sort = categoryNameEntity.sort;
        categoryName.status = categoryNameEntity.status;
        categoryName.iocn = categoryNameEntity.iocn;
        categoryName.color = categoryNameEntity.color;
        return categoryName;
    }
}
