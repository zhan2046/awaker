package com.future.awaker.imageloader;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.future.awaker.R;

/**
 * Copyright ©2017 by Teambition
 */

public class GlideImpl implements IImageLoader {

    private static final int DEFAULT_DURATION_MS = 600;
    private static final float SIZE_MULTIPLIER = 0.5f;

    private DrawableTransitionOptions normalTransitionOptions = new DrawableTransitionOptions()
            .crossFade(DEFAULT_DURATION_MS);

    @Override
    public void load(ImageView imageView, String url) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .transition(normalTransitionOptions)
                .placeholder(R.drawable.image_mark)
                .error(R.drawable.image_mark)
                .into(imageView);
    }

    @Override
    public void load(ImageView imageView, String url, RequestListener<Drawable> listener) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .transition(normalTransitionOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        listener.onLoadFailed(e, model, target, isFirstResource);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        listener.onResourceReady(resource, model, target, dataSource, isFirstResource);
                        return false;
                    }
                })
                .into(imageView);
    }

    @Override
    public void load(ImageView imageView, int resId, RequestListener<Drawable> listener) {
        GlideApp.with(imageView.getContext())
                .load(resId)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        listener.onLoadFailed(e, model, target, isFirstResource);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model,
                                                   Target<Drawable> target, DataSource dataSource,
                                                   boolean isFirstResource) {
                        listener.onResourceReady(resource, model, target, dataSource,
                                isFirstResource);
                        return false;
                    }
                })
                .transition(normalTransitionOptions)
                .into(imageView);
    }

    @Override
    public void loadThumb(ImageView imageView, String url) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .thumbnail(SIZE_MULTIPLIER)
                .transition(normalTransitionOptions)
                .placeholder(R.drawable.image_mark)
                .error(R.drawable.image_mark)
                .into(imageView);
    }

    @Override
    public void loadCropCircle(ImageView imageView, String url) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .transition(normalTransitionOptions)
                .transform(new MultiTransformation<>(new CircleCrop()))
                .placeholder(R.drawable.image_circle_mark)
                .error(R.drawable.image_circle_mark)
                .into(imageView);
    }
}
