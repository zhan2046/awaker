package com.future.awaker.helper;

import android.content.Context;

import com.future.awaker.Account;
import com.future.awaker.Application;
import com.future.awaker.data.UserInfo;
import com.future.awaker.db.entity.UserInfoEntity;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.source.AwakerRepository;
import com.future.awaker.util.LogUtils;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright ©2017 by ruzhan
 */

public final class AccountHelper {

    private static final String TAG = "AccountHelper";


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
        AwakerRepository.get().loadUserInfoEntity(UserInfoEntity.ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "initLocalNews doOnError: " + throwable.toString()))
                .doOnNext(userInfoEntity -> {
                    if (userInfoEntity != null) {
                        Account.get().setUserInfoToLocal(userInfoEntity.userInfo);
                    }
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public static void setUserInfoToLocal(UserInfo userInfo) {
        Flowable.create(e -> {
            UserInfoEntity userInfoEntity = new UserInfoEntity(UserInfoEntity.ID, userInfo);
            AwakerRepository.get().insertUserInfoEntity(userInfoEntity);
            e.onComplete();

        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "setUserInfoToLocal doOnError: " + throwable.toString()))
                .doOnComplete(() -> {
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public static void clearUserInfo() {
        Flowable.create(e -> {
            UserInfoEntity userInfoEntity = new UserInfoEntity(UserInfoEntity.ID, null);
            AwakerRepository.get().insertUserInfoEntity(userInfoEntity);
            e.onComplete();

        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "setUserInfoToLocal doOnError: " + throwable.toString()))
                .doOnComplete(() -> {
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
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
