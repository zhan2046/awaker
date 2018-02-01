package com.future.awaker.databinding;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.future.awaker.imageloader.ImageLoader;

/**
 * Created by ruzhan on 2017/7/15.
 */

public final class DataBindingAdapter {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView iv, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            ImageLoader.get().load(iv, imageUrl);
        }
    }

    @BindingAdapter({"imageThumbUrl"})
    public static void loadImageThumb(ImageView iv, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            ImageLoader.get().loadThumb(iv, imageUrl);
        }
    }

    @BindingAdapter({"imageCropCircleUrl"})
    public static void loadImageCropCircle(ImageView iv, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            ImageLoader.get().loadCropCircle(iv, imageUrl);
        }
    }

    @BindingAdapter({"imageCropCircleUrlRes"})
    public static void loadImageCropCircle(ImageView iv, int resId) {
        if (resId > 0) {
            ImageLoader.get().loadCropCircle(iv, resId);
        }
    }
}
