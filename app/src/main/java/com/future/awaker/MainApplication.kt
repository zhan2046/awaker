package com.future.awaker

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.future.common.CommonUtils
import com.future.font.FontHelper
import com.future.media.MediaControllerManager

class MainApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        CommonUtils.get().init(this)
        FontHelper.init(this)
        MediaControllerManager.init(this)
    }
}
