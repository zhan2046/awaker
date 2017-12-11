package com.future.awaker.helper;

import android.content.Context;

import com.future.awaker.Account;
import com.future.awaker.Application;
import com.future.awaker.data.UserInfo;
import com.future.awaker.data.realm.UserInfoRealm;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.source.AwakerRepository;
import com.future.awaker.util.LogUtils;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Copyright ©2017 by ruzhan
 */

public final class AccountHelper {

    private static final String TAG = AccountHelper.class.getSimpleName();


    public static final String OPEN_ID = "openId";
    public static final String USER_NAME = "userName";

    private AccountHelper() {
    }

    public static String getOpenId() {
        return Application.get().getSharedPreferences(OPEN_ID, Context.MODE_PRIVATE)
                .getString(OPEN_ID, "");
    }

    public static String getUserName() {
        return Application.get().getSharedPreferences(OPEN_ID, Context.MODE_PRIVATE)
                .getString(USER_NAME, "");
    }

    public static void initUserInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put(UserInfoRealm.ID, UserInfoRealm.ID_VALUE);
        AwakerRepository.get().getLocalRealm(UserInfoRealm.class, map)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "doOnError: " + throwable.toString()))
                .doOnNext(realmResults -> {
                    LogUtils.d("initUserInfo: " + realmResults.size());
                    Account.get().setLocalUserInfo(realmResults);

                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public static void setUserInfoToLocal(UserInfo userInfo) {
        UserInfoRealm userInfoRealm = UserInfoRealm.getUserInfoRealm(userInfo);
        AwakerRepository.get().updateLocalRealm(userInfoRealm);
    }

    public static void clearUserInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put(UserInfoRealm.ID, UserInfoRealm.ID_VALUE);
        AwakerRepository.get().deleteLocalRealm(UserInfoRealm.class, map);
    }

    public static void setOpenIdToLocal(String openId) {
        Application.get().getSharedPreferences(OPEN_ID, Context.MODE_PRIVATE)
                .edit()
                .putString(OPEN_ID, openId)
                .apply();
    }

    public static void setUserNameToLocal(String userName) {
        Application.get().getSharedPreferences(OPEN_ID, Context.MODE_PRIVATE)
                .edit()
                .putString(USER_NAME, userName)
                .apply();
    }
}
