package com.awaker.imageloader

import android.widget.ImageView

interface IImageLoader {

    fun load(imageView: ImageView, url: String)
}
