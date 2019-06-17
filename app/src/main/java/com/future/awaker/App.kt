package com.future.awaker

import android.content.Context
import android.support.multidex.MultiDex

import com.blankj.utilcode.util.Utils
import com.ruzhan.common.util.CommonUtils

class App : android.app.Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        CommonUtils.setApplication(this)
        com.ruzhan.lion.App.setApp(this)
        Utils.init(this)
    }

    companion object {

        private var INSTANCE: App? = null

        fun get(): App? {
            return INSTANCE
        }
    }
}
