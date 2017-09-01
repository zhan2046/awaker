package com.future.awaker.imageloader;

import com.future.awaker.imageloader.glide.GlideImpl;
import com.poet.compiler.awaker.ImageLoaderImpl;

/**
 * Copyright ©2017 by ruzhan
 */

public final class ImageLoader {

    private static IImageLoader imageLoader;

    private ImageLoader() {
    }

    public static IImageLoader get() {
        if (imageLoader == null) {
            synchronized (ImageLoader.class) {
                if (imageLoader == null) {
                    imageLoader = new ImageLoaderImpl(new GlideImpl());
                }
            }
        }
        return imageLoader;
    }
}
