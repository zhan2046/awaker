package com.future.awaker.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

/**
 * Copyright ©2017 by Teambition
 */

public class GlideImpl implements IImageLoader {

    private static final int DEFAULT_DURATION_MS = 600;

    private DrawableTransitionOptions normalTransitionOptions = new DrawableTransitionOptions()
            .crossFade(DEFAULT_DURATION_MS);

    @Override
    public void load(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .transition(normalTransitionOptions)
                .into(imageView);
    }

    @Override
    public void loadThumb(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .thumbnail(0.5f)
                .transition(normalTransitionOptions)
                .into(imageView);
    }
}
