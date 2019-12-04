package com.future.awaker

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.blankj.utilcode.util.Utils
import com.ruzhan.day.util.ResUtils
import com.ruzhan.font.FontHelper
import com.ruzhan.movie.utils.X5Helper

class MainApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        ResUtils.init(this)
        com.ruzhan.movie.utils.ResUtils.init(this)
        X5Helper.init(this)
        FontHelper.init(this)
        Utils.init(this)
    }
}
