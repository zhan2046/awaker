package com.future.awaker;

import com.future.awaker.data.UserInfo;

/**
 * Copyright ©2017 by Teambition
 */

public final class Account {

    private static final String TAG = Account.class.getSimpleName();

    private static Account INSTANCE;

    private UserInfo userInfo;

    private Account() {
    }

    public static Account get() {
        if (INSTANCE == null) {
            synchronized (Account.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Account();
                }
            }
        }
        return INSTANCE;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfoToLocal(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
