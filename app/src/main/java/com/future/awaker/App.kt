package com.future.awaker

import android.content.Context
import android.support.multidex.MultiDex
import com.blankj.utilcode.util.Utils
import com.ruzhan.common.util.ResUtils

class App : android.app.Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        ResUtils.init(this)
        com.ruzhan.lion.App.setApp(this)
        Utils.init(this)
    }
}
