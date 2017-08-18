package com.future.awaker.data.realm;

import com.future.awaker.data.UserAuth;

import io.realm.RealmObject;

/**
 * Copyright ©2017 by ruzhan
 */

public class UserAuthRealm extends RealmObject {

    public String uid;
    public String username;
    public String last_login_time;
    public String role_id;
    public String audit;

    public static UserAuthRealm getUserAuthRealm(UserAuth userAuth) {
        UserAuthRealm userAuthRealm = new UserAuthRealm();
        if (userAuth != null) {
            userAuthRealm.uid = userAuth.uid;
            userAuthRealm.username = userAuth.username;
            userAuthRealm.last_login_time = userAuth.last_login_time;
            userAuthRealm.role_id = userAuth.role_id;
            userAuthRealm.audit = userAuth.audit;
        }
        return userAuthRealm;
    }

    public static UserAuth getUserAuth(UserAuthRealm userAuthRealm) {
        UserAuth userAuth = new UserAuth();
        if (userAuthRealm != null) {
            userAuth.uid = userAuthRealm.uid;
            userAuth.username = userAuthRealm.username;
            userAuth.last_login_time = userAuthRealm.last_login_time;
            userAuth.role_id = userAuthRealm.role_id;
            userAuth.audit = userAuthRealm.audit;
        }
        return userAuth;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public String getAudit() {
        return audit;
    }

    public void setAudit(String audit) {
        this.audit = audit;
    }
}
