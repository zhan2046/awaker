package com.ruzhan.awaker.article.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.ruzhan.awaker.article.model.UserInfo;


@Entity(tableName = "user_info_entity")
public class UserInfoEntity {

    public static final String ID = "user_info_entity";

    @NonNull
    @PrimaryKey
    public String id;
    @Embedded(prefix = "user_info")
    public UserInfo userInfo;

    @Ignore
    public UserInfoEntity() {

    }

    public UserInfoEntity(@NonNull String id, UserInfo userInfo) {
        this.id = id;
        this.userInfo = userInfo;
    }
}
