package com.future.awaker.data.realm;

import com.future.awaker.data.UserData;

import io.realm.RealmObject;

/**
 * Copyright ©2017 by Teambition
 */

public class UserDataRealm extends RealmObject {

    public String open_id;
    public UserAuthRealm auth;
    public int timestamp;

    public static UserDataRealm getUserDataRealm(UserData userData) {
        UserDataRealm userDataRealm = new UserDataRealm();
        if (userData != null) {
            userDataRealm.open_id = userData.open_id;
            userDataRealm.auth = UserAuthRealm.getUserAuthRealm(userData.auth);
            userDataRealm.timestamp = userData.timestamp;
        }
        return userDataRealm;
    }

    public static UserData getUserData(UserDataRealm userDataRealm) {
        UserData userData = new UserData();
        if (userDataRealm != null) {
            userData.open_id = userDataRealm.open_id;
            userData.auth = UserAuthRealm.getUserAuth(userDataRealm.auth);
            userData.timestamp = userDataRealm.timestamp;
        }
        return userData;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public UserAuthRealm getAuth() {
        return auth;
    }

    public void setAuth(UserAuthRealm auth) {
        this.auth = auth;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
