package com.future.font

import android.app.Application
import android.graphics.Typeface

class FontHelper private constructor() {

    val lightFontTypeface: Typeface by lazy {
        Typeface.createFromAsset(application?.assets, TEXT_LIGHT)
    }
    val boldFontTypeface: Typeface by lazy {
        Typeface.createFromAsset(application?.assets, TEXT_BOLD)
    }

    companion object {
        const val TEXT_BOLD = "fonts/text_bold.ttf"
        const val TEXT_LIGHT = "fonts/text_light.ttf"

        private var application: Application? = null
        @Volatile
        private var INSTANCE: FontHelper? = null

        @JvmStatic
        fun get(): FontHelper = INSTANCE ?: synchronized(FontHelper::class) {
            INSTANCE ?: FontHelper().also { INSTANCE = it }
        }

        @JvmStatic
        fun init(application: Application) {
            this.application = application
        }
    }
}