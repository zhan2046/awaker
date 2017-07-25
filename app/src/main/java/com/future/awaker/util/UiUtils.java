package com.future.awaker.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.future.awaker.Application;

/**
 * Copyright ©2017 by Teambition
 */

public final class UiUtils {

    private UiUtils() {
    }

    public static Context getContext() {
        return Application.get();
    }

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */
    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= 13) {
            display.getSize(size);
            return size.y;
        } else {
            return display.getHeight();
        }
    }

    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= 13) {
            display.getSize(size);
            return size.x;
        } else {
            return display.getWidth();
        }
    }

    public static int getVersionCode(Context context) {
        if (context != null) {
            try {
                return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException ignored) {
            }
        }
        return 0;
    }

    public static void setTint(Context context, int DrawableRes, int tintColor, ImageView imageView) {
        Drawable up = ContextCompat.getDrawable(context, DrawableRes);
        Drawable drawableUp = DrawableCompat.wrap(up);
        DrawableCompat.setTint(drawableUp, ContextCompat.getColor(context, tintColor));
        imageView.setImageDrawable(drawableUp);
    }
}
