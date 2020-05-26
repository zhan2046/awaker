package com.future.movie.utils

import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.IntRange

object ColorUtils {

    @CheckResult
    @ColorInt
    fun modifyAlpha(@ColorInt color: Int,
                    @IntRange(from = 0, to = 255) alpha: Int): Int {
        return color and 0x00ffffff or (alpha shl 24)
    }

    @CheckResult
    @ColorInt
    fun modifyAlpha(@ColorInt color: Int,
                    @FloatRange(from = 0.0, to = 1.0) alpha: Float): Int {
        return modifyAlpha(color, (255f * alpha).toInt())
    }
}
