package com.ruzhan.awaker.article.imageloader.glide

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView

import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ruzhan.awaker.article.imageloader.IImageLoader
import com.ruzhan.common.R
import com.ruzhan.lion.glide.GlideApp

class GlideImpl : IImageLoader {

    private val normalTransitionOptions = DrawableTransitionOptions()
            .crossFade()

    override fun load(imageView: ImageView, url: String) {
        GlideApp.with(imageView.context)
                .load(url)
                .transition(normalTransitionOptions)
                .placeholder(R.drawable.awaker_article_image_mark)
                .error(R.drawable.awaker_article_image_mark)
                .into(imageView)
    }

    override fun load(imageView: ImageView, url: String, listener: RequestListener<Drawable>) {
        GlideApp.with(imageView.context)
                .load(url)
                .transition(normalTransitionOptions)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        listener.onLoadFailed(e, model, target, isFirstResource)
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        listener.onResourceReady(resource, model, target, dataSource, isFirstResource)
                        return false
                    }
                })
                .into(imageView)
    }

    override fun load(imageView: ImageView, resId: Int, listener: RequestListener<Drawable>) {
        GlideApp.with(imageView.context)
                .load(resId)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any,
                                              target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        listener.onLoadFailed(e, model, target, isFirstResource)
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any,
                                                 target: Target<Drawable>, dataSource: DataSource,
                                                 isFirstResource: Boolean): Boolean {
                        listener.onResourceReady(resource, model, target, dataSource,
                                isFirstResource)
                        return false
                    }
                })
                .transition(normalTransitionOptions)
                .into(imageView)
    }

    override fun loadThumb(imageView: ImageView, url: String) {
        GlideApp.with(imageView.context)
                .load(url)
                .thumbnail(SIZE_MULTIPLIER)
                .transition(normalTransitionOptions)
                .placeholder(R.drawable.awaker_article_image_mark)
                .error(R.drawable.awaker_article_image_mark)
                .into(imageView)
    }

    override fun loadCropCircle(imageView: ImageView, url: String) {
        GlideApp.with(imageView.context)
                .load(url)
                .transition(normalTransitionOptions)
                .transform(MultiTransformation<Bitmap>(CircleCrop()))
                .placeholder(R.drawable.awaker_article_image_circle_mark)
                .error(R.drawable.awaker_article_image_circle_mark)
                .into(imageView)
    }

    override fun loadCropCircle(imageView: ImageView, resId: Int) {
        GlideApp.with(imageView.context)
                .load(resId)
                .transition(normalTransitionOptions)
                .transform(MultiTransformation<Bitmap>(CircleCrop()))
                .placeholder(R.drawable.awaker_article_image_circle_mark)
                .error(R.drawable.awaker_article_image_circle_mark)
                .into(imageView)
    }

    companion object {

        private val SIZE_MULTIPLIER = 0.3f
    }
}
