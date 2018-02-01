package com.future.awaker;

import com.future.awaker.data.UserInfo;
import com.future.awaker.helper.AccountHelper;

/**
 * Copyright ©2017 by ruzhan
 */

public final class Account {

    private static final String TAG = Account.class.getSimpleName();

    private static Account INSTANCE;

    private String openId;
    private String userName;
    private UserInfo userInfo;

    private Account() {
        openId = AccountHelper.getOpenId();
        userName = AccountHelper.getUserName();
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

    public boolean isLogin() {
        return userInfo != null;
    }

    public void initUserInfo() {
        AccountHelper.initUserInfo();
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfoToLocal(UserInfo userInfo) {
        this.userInfo = userInfo;
        if (userInfo != null) {
            setOpenIdToLocal(userInfo.data == null ? "" : userInfo.data.open_id);
            setUserNameToLocal(userInfo.data_1 == null ? "" : userInfo.data_1.nickname);
        }
        AccountHelper.setUserInfoToLocal(userInfo);
    }

    public void clearUserInfo() {
        userInfo = null;
        openId = null;
        setOpenIdToLocal("");
        setUserNameToLocal("");

        AccountHelper.clearUserInfo();
    }

    public void setOpenIdToLocal(String openId) {
        this.openId = openId;
        AccountHelper.setOpenIdToLocal(openId);
    }

    public void setUserNameToLocal(String userName) {
        this.userName = userName;
        AccountHelper.setUserNameToLocal(userName);
    }

    public String getOpenId() {
        return openId;
    }

    public String getUserName() {
        return userName;
    }
}
