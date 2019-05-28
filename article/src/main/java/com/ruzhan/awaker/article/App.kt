package com.ruzhan.awaker.article

import android.content.Context
import android.support.multidex.MultiDex

import com.blankj.utilcode.util.Utils

class App : android.app.Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Utils.init(this)
        com.ruzhan.lion.App.setApp(this)
    }

    companion object {

        private var INSTANCE: App? = null

        fun get(): App? {
            return INSTANCE
        }
    }
}
