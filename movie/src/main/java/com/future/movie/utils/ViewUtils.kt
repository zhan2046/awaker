package com.future.movie.utils

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.future.movie.R

object ViewUtils {

    val DRAWABLE_ALPHA =
        AnimUtils.createIntProperty(object : AnimUtils.IntProp<Drawable>("alpha") {
            override fun set(`object`: Drawable, value: Int) {
                `object`.alpha = value
            }

            override fun get(`object`: Drawable): Int {
                return `object`.alpha
            }
        })

    fun getPlaceholder(context: Context, position: Int): Drawable {
        val placeholderColors =
                context.resources.getIntArray(R.array.loading_placeholders_grey)
        return ColorDrawable(placeholderColors[position % placeholderColors.size])
    }

    fun isNavBarOnBottom(context: Context): Boolean {
        val res = context.resources
        val cfg = context.resources.configuration
        val dm = res.displayMetrics
        val canMove = dm.widthPixels != dm.heightPixels && cfg.smallestScreenWidthDp < 600
        return !canMove || dm.widthPixels < dm.heightPixels
    }
}
