package com.love.awaker.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

/**
 * Copyright ©2017 by Teambition
 */

public class GlideImpl implements IImageLoader {

    private DrawableTransitionOptions normalTransitionOptions = new DrawableTransitionOptions()
            .crossFade();

    @Override
    public void load(Context context, String url, ImageView imageView) {
        GlideApp.with(context)
                .load(url)
                .transition(normalTransitionOptions)
                .into(imageView);
    }
}
