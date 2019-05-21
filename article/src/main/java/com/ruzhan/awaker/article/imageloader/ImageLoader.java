package com.ruzhan.awaker.article.imageloader;

import com.ruzhan.awaker.article.imageloader.glide.GlideImpl;

public final class ImageLoader {

    private static IImageLoader imageLoader;

    private ImageLoader() {
    }

    public static IImageLoader get() {
        if (imageLoader == null) {
            synchronized (ImageLoader.class) {
                if (imageLoader == null) {
                    imageLoader = new GlideImpl();
                }
            }
        }
        return imageLoader;
    }
}
