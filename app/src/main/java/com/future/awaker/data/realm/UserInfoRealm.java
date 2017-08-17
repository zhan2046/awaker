package com.future.awaker.data.realm;

import com.future.awaker.data.UserInfo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Copyright ©2017 by Teambition
 */

public class UserInfoRealm extends RealmObject {

    public static final String ID = "user_info_id";
    public static final String ID_VALUE = "0";

    @PrimaryKey
    public String user_info_id = ID_VALUE;
    public UserDataRealm data;
    public UserDetailRealm data_1;
    public String info;
    public int code;

    public static UserInfoRealm getUserInfoRealm(UserInfo userInfo) {
        UserInfoRealm userInfoRealm = new UserInfoRealm();
        if (userInfo != null) {
            userInfoRealm.data = UserDataRealm.getUserDataRealm(userInfo.data);
            userInfoRealm.data_1 = UserDetailRealm.getUserDetailRealm(userInfo.data_1);
            userInfoRealm.info = userInfo.info;
            userInfoRealm.code = userInfo.code;
        }
        return userInfoRealm;
    }

    public static UserInfo getUserInfo(UserInfoRealm userInfoRealm) {
        UserInfo userInfo = new UserInfo();
        if (userInfoRealm != null) {
            userInfo.data = UserDataRealm.getUserData(userInfoRealm.data);
            userInfo.data_1 = UserDetailRealm.getUserDetail(userInfoRealm.data_1);
            userInfo.info = userInfoRealm.info;
            userInfo.code = userInfoRealm.code;
        }
        return userInfo;
    }

    public String getUser_info_id() {
        return user_info_id;
    }

    public void setUser_info_id(String user_info_id) {
        this.user_info_id = user_info_id;
    }

    public UserDataRealm getData() {
        return data;
    }

    public void setData(UserDataRealm data) {
        this.data = data;
    }

    public UserDetailRealm getData_1() {
        return data_1;
    }

    public void setData_1(UserDetailRealm data_1) {
        this.data_1 = data_1;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
