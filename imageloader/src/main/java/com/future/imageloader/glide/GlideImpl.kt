package com.future.imageloader.glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions

class GlideImpl : IImageLoader {

    private val requestOptions = RequestOptions().centerCrop()
    private val transitionOptions = DrawableTransitionOptions.withCrossFade()

    override fun load(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .apply(requestOptions)
            .transition(transitionOptions)
            .into(imageView)
    }

    override fun load(imageView: ImageView, resId: Int) {
        Glide.with(imageView.context)
            .load(resId)
            .apply(requestOptions)
            .transition(transitionOptions)
            .into(imageView)
    }

    override fun load(imageView: ImageView, url: String, listener: RequestListener<Drawable>) {
        Glide.with(imageView.context)
            .load(url)
            .apply(requestOptions)
            .transition(transitionOptions)
            .listener(listener)
            .into(imageView)
    }

    override fun load(imageView: ImageView, resId: Int, listener: RequestListener<Drawable>) {
        Glide.with(imageView.context)
            .load(resId)
            .apply(requestOptions)
            .transition(transitionOptions)
            .listener(listener)
            .into(imageView)
    }

    override fun load(imageView: ImageView, url: String, placeholder: Drawable?) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(placeholder)
            .apply(requestOptions)
            .transition(transitionOptions)
            .into(imageView)
    }

    override fun load(imageView: ImageView, resId: Int, placeholder: Drawable?) {
        Glide.with(imageView.context)
            .load(resId)
            .placeholder(placeholder)
            .apply(requestOptions)
            .transition(transitionOptions)
            .into(imageView)
    }

    override fun load(imageView: ImageView, url: String, placeholder: Drawable?,
                      listener: RequestListener<Drawable>) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(placeholder)
            .apply(requestOptions)
            .transition(transitionOptions)
            .listener(listener)
            .into(imageView)
    }

    override fun load(imageView: ImageView, resId: Int, placeholder: Drawable?,
                      listener: RequestListener<Drawable>) {
        Glide.with(imageView.context)
            .load(resId)
            .placeholder(placeholder)
            .apply(requestOptions)
            .transition(transitionOptions)
            .listener(listener)
            .into(imageView)
    }

    override fun loadNoCrossFade(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
            .load(url)
            .apply(requestOptions)
            .into(imageView)
    }

    override fun loadNoCrossFade(imageView: ImageView, resId: Int) {
        Glide.with(imageView.context)
            .load(resId)
            .apply(requestOptions)
            .into(imageView)
    }

    override fun loadNoCrossFade(imageView: ImageView, url: String, placeholder: Drawable) {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(placeholder)
            .apply(requestOptions)
            .into(imageView)
    }

    override fun loadNoCrossFade(imageView: ImageView, resId: Int, placeholder: Drawable) {
        Glide.with(imageView.context)
            .load(resId)
            .placeholder(placeholder)
            .apply(requestOptions)
            .into(imageView)
    }
}
