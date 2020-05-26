package com.future.day.util

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.future.day.R

object DrawableUtils {

    fun getPlaceholder(): Drawable {
        return ColorDrawable(ContextCompat.getColor(ResUtils.context,
                R.color.loading_placeholders_grey))
    }
}