package com.love.awaker.imageloader;

import android.content.Context;
import android.widget.ImageView;

/**
 * Copyright ©2017 by Teambition
 */

public interface IImageLoader {

    void load(Context context, String url, ImageView imageView);
}
