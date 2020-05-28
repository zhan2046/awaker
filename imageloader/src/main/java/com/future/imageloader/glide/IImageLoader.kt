package com.future.imageloader.glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.RequestListener

interface IImageLoader {

    fun load(imageView: ImageView, url: String)

    fun load(imageView: ImageView, resId: Int)

    fun load(imageView: ImageView, url: String, listener: RequestListener<Drawable>)

    fun load(imageView: ImageView, resId: Int, listener: RequestListener<Drawable>)

    fun load(imageView: ImageView, url: String, placeholder: Drawable?)

    fun load(imageView: ImageView, resId: Int, placeholder: Drawable?)

    fun load(imageView: ImageView, url: String, placeholder: Drawable?,
             listener: RequestListener<Drawable>)

    fun load(imageView: ImageView, resId: Int, placeholder: Drawable?,
             listener: RequestListener<Drawable>)

    fun loadNoCrossFade(imageView: ImageView, url: String)

    fun loadNoCrossFade(imageView: ImageView, resId: Int)

    fun loadNoCrossFade(imageView: ImageView, url: String, placeholder: Drawable)

    fun loadNoCrossFade(imageView: ImageView, resId: Int, placeholder: Drawable)
}