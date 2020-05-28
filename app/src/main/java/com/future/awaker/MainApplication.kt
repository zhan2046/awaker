package com.future.awaker

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.future.media.MediaControllerManager
import com.future.day.util.ResUtils
import com.future.font.FontHelper

class MainApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        ResUtils.init(this)
        com.future.movie.utils.ResUtils.init(this)
        FontHelper.init(this)
        MediaControllerManager.init(this)
    }
}
