package com.future.movie.utils

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.future.movie.R

object ViewUtils {

    fun getPlaceholder(context: Context, position: Int): Drawable {
        val placeholderColors =
            context.resources.getIntArray(R.array.loading_placeholders_grey)
        return ColorDrawable(placeholderColors[position % placeholderColors.size])
    }
}
