package com.ruzhan.common.util

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

object ResUtils {

    private var APPLICATION: Application? = null

    @JvmStatic
    val context: Context
        get() = APPLICATION!!

    @JvmStatic
    fun init(application: Application) {
        APPLICATION = application
    }

    @JvmStatic
    fun getString(strId: Int): String {
        return if (strId <= 0) {
            ""
        } else context.resources.getString(strId) ?: ""
    }
}
