package com.ruzhan.awaker.article.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ruzhan.awaker.article.App;

public final class UiUtils {

    private UiUtils() {
    }

    public static Context getContext() {
        return App.get();
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
        if (wm == null) {
            return 0;
        }
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return 0;
        }
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
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
