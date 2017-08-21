package com.future.awaker;

import android.content.Context;

import com.future.awaker.data.UserInfo;
import com.future.awaker.data.realm.UserInfoRealm;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.source.AwakerRepository;
import com.future.awaker.util.LogUtils;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by ruzhan
 */

public final class Account {

    private static final String TAG = Account.class.getSimpleName();

    public static final String OPEN_ID = "openId";
    public static final String USER_NAME = "userName";

    private static Account INSTANCE;

    private String openId;
    private String userName;
    private UserInfo userInfo;

    private Account() {
        openId = Application.get().getSharedPreferences(OPEN_ID, Context.MODE_PRIVATE)
                .getString(OPEN_ID, "");
        userName = Application.get().getSharedPreferences(OPEN_ID, Context.MODE_PRIVATE)
                .getString(USER_NAME, "");
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
        HashMap<String, String> map = new HashMap<>();
        map.put(UserInfoRealm.ID, UserInfoRealm.ID_VALUE);
        AwakerRepository.get().getLocalRealm(UserInfoRealm.class, map)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "doOnError: " + throwable.toString()))
                .doOnNext(realmResults -> {
                    LogUtils.d("initUserInfo: " + realmResults.size());
                    setLocalUserInfo(realmResults);

                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());

    }

    private void setLocalUserInfo(RealmResults realmResults) {
        if (realmResults == null || realmResults.isEmpty()) {
            return;
        }
        UserInfoRealm userInfoRealm =
                (UserInfoRealm) realmResults.get(0);
        UserInfo userInfo = UserInfoRealm.getUserInfo(userInfoRealm);
        setUserInfo(userInfo);
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfoToLocal(UserInfo userInfo) {
        this.userInfo = userInfo;

        UserInfoRealm userInfoRealm = UserInfoRealm.getUserInfoRealm(userInfo);
        AwakerRepository.get().updateLocalRealm(userInfoRealm);
    }

    public void clearUserInfo() {
        userInfo = null;
        openId = null;
        setOpenIdToLocal("");
        setUserNameToLocal("");

        HashMap<String, String> map = new HashMap<>();
        map.put(UserInfoRealm.ID, UserInfoRealm.ID_VALUE);
        AwakerRepository.get().deleteLocalRealm(UserInfoRealm.class, map);
    }

    public void setOpenIdToLocal(String openId) {
        this.openId = openId;
        Application.get().getSharedPreferences(OPEN_ID, Context.MODE_PRIVATE)
                .edit()
                .putString(OPEN_ID, openId)
                .apply();
    }

    public void setUserNameToLocal(String userName) {
        this.userName = userName;
        Application.get().getSharedPreferences(OPEN_ID, Context.MODE_PRIVATE)
                .edit()
                .putString(USER_NAME, userName)
                .apply();
    }

    public String getOpenId() {
        return openId;
    }

    public String getUserName() {
        return userName;
    }
}
