package com.future.awaker.db.entity;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.future.awaker.data.UserInfo;


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
