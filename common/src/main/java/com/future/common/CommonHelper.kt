package com.future.common

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.view.View

@SuppressLint("ObsoleteSdkInt")
object CommonHelper {

    fun hideNavigationBar(activity: Activity) {
        val decorView = activity.window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val flags = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_FULLSCREEN)
            decorView.systemUiVisibility = flags
        } else {
            decorView.systemUiVisibility = View.GONE
        }
    }

    fun showNavigationBar(activity: Activity) {
        val decorView = activity.window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val flags = View.SYSTEM_UI_FLAG_VISIBLE
            decorView.systemUiVisibility = flags
        } else {
            decorView.systemUiVisibility = View.VISIBLE
        }
    }
}