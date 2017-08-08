package com.future.awaker;

import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class Application extends android.app.Application {

    private static final String AWAKER_DB = "awakerDB";
    private static final int VERSION_CODE = 0;

    private static Application INSTANCE;

    public static Application get() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        LeakCanary.install(this);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(AWAKER_DB)
                .schemaVersion(VERSION_CODE)
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
