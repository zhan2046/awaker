package com.ruzhan.common.util

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat

object ResUtils {

    private const val INVALID_COLOR = -1

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

    @JvmStatic
    fun getStringArray(strArrayId: Int): Array<String>? {
        return if (strArrayId <= 0) {
            null
        } else context.resources.getStringArray(strArrayId)
    }

    @JvmStatic
    fun getString(strId: Int, vararg formatArgs: Any): String {
        return if (strId <= 0) {
            ""
        } else context.resources.getString(strId, *formatArgs) ?: ""
    }

    @JvmStatic
    fun getColor(colorId: Int): Int {
        return if (colorId <= 0) {
            INVALID_COLOR
        } else context.resources.getColor(colorId)
    }

    @JvmStatic
    fun getDrawable(drawableId: Int): Drawable? {
        return if (drawableId <= 0) {
            null
        } else context.resources.getDrawable(drawableId)
    }

    @JvmStatic
    fun setTint(context: Context, DrawableRes: Int, tintColor: Int, imageView: ImageView) {
        val up = ContextCompat.getDrawable(context, DrawableRes)
        val drawableUp = DrawableCompat.wrap(up!!)
        DrawableCompat.setTint(drawableUp, ContextCompat.getColor(context, tintColor))
        imageView.setImageDrawable(drawableUp)
    }
}
