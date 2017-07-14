package com.future.awaker.imageloader;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestListener;

/**
 * Copyright ©2017 by Teambition
 */

public interface IImageLoader {

    void load(ImageView imageView, String url);

    void load(ImageView imageView, String url, RequestListener<Drawable> listener);

    void load(ImageView imageView, int resId, RequestListener<Drawable> listener);

    void loadThumb(ImageView imageView, String url);
}
