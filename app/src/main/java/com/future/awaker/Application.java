package com.future.awaker;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.Utils;
import com.crashlytics.android.Crashlytics;
import com.future.awaker.util.ConstantUtils;
import com.ruzhan.lion.App;

import io.fabric.sdk.android.Fabric;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class Application extends android.app.Application {

    private static Application INSTANCE;

    public static Application get() {
        return INSTANCE;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        Utils.init(Application.get());
        App.setApp(this);

        if (ConstantUtils.isReleaseBuild()) {
            Fabric.with(this, new Crashlytics());
        }
    }
}
