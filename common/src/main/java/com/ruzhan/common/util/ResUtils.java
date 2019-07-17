package com.ruzhan.common.util;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.ImageView;

public final class ResUtils {

    private static final int INVALID_COLOR = -1;

    private static Application APPLICATION;

    private ResUtils() {
        // do nothing
    }

    public static void init(Application application) {
        APPLICATION = application;
    }

    public static Context getContext() {
        return APPLICATION;
    }

    public static String getString(int strId) {
        if (strId <= 0) {
            return null;
        }
        return getContext().getResources().getString(strId);
    }

    public static String[] getStringArray(int strArrayId) {
        if (strArrayId <= 0) {
            return null;
        }
        return getContext().getResources().getStringArray(strArrayId);
    }

    public static String getString(int strId, Object... formatArgs) {
        if (strId <= 0) {
            return null;
        }
        return getContext().getResources().getString(strId, formatArgs);
    }

    public static int getColor(int colorId) {
        if (colorId <= 0) {
            return INVALID_COLOR;
        }
        return getContext().getResources().getColor(colorId);
    }

    public static Drawable getDrawable(int drawableId) {
        if (drawableId <= 0) {
            return null;
        }
        return getContext().getResources().getDrawable(drawableId);
    }

    public static void setTint(Context context, int DrawableRes, int tintColor, ImageView imageView) {
        Drawable up = ContextCompat.getDrawable(context, DrawableRes);
        Drawable drawableUp = DrawableCompat.wrap(up);
        DrawableCompat.setTint(drawableUp, ContextCompat.getColor(context, tintColor));
        imageView.setImageDrawable(drawableUp);
    }
}
