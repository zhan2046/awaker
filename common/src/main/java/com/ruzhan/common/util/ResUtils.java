package com.ruzhan.common.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

public final class ResUtils {

    private static final int INVALID_COLOR = -1;

    private ResUtils() {
        // do nothing
    }

    private static Context getContext() {
        return CommonUtils.getContext();
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
}
