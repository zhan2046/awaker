package com.awaker.imageloader

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

class GlideImpl : IImageLoader {

    override fun load(imageView: ImageView, url: String) {
        val placeholder = ColorDrawable(ContextCompat
                .getColor(imageView.context, R.color.loading_placeholders_grey))
        val requestOptions = RequestOptions()
                .centerCrop()
                .placeholder(placeholder)
                .error(placeholder)
        Glide.with(imageView.context)
                .load(url)
                .apply(requestOptions)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
    }
}
