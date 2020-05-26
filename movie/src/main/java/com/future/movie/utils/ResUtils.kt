package com.future.movie.utils

import android.app.Application

object ResUtils {

    private lateinit var application: Application

    @JvmStatic
    fun init(application: Application) {
        ResUtils.application = application
    }

    @JvmStatic
    fun getApp(): Application = application
}