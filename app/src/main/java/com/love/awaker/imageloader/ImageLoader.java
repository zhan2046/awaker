package com.love.awaker.imageloader;

/**
 * Copyright ©2017 by Teambition
 */

public final class ImageLoader {

    private static GlideImpl GLIDE;

    private ImageLoader() {}

    public static IImageLoader get() {
        if (GLIDE == null) {
            synchronized (ImageLoader.class) {
                if (GLIDE == null) {
                    GLIDE = new GlideImpl();
                }
            }
        }
        return GLIDE;
    }
}
