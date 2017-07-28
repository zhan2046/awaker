package com.future.awaker.util;

import android.graphics.drawable.Drawable;

import com.future.awaker.Application;

/**
 * Created by ruzhan on 2017/7/6.
 */

public final class ResUtils {

  public static final int INVALID_COLOR = -1;

  private ResUtils() {
  }

  public static String getString(int strId) {
    if (strId <= 0) {
      return null;
    }
    return Application.get().getResources().getString(strId);
  }

  public static String[] getStringArray(int strArrayId) {
    if (strArrayId <= 0) {
      return null;
    }
    return Application.get().getResources().getStringArray(strArrayId);
  }

  public static String getString(int strId, Object... formatArgs) {
    if (strId <= 0) {
      return null;
    }
    return Application.get().getResources().getString(strId, formatArgs);
  }

  public static int getColor(int colorId) {
    if (colorId <= 0) {
      return INVALID_COLOR;
    }
    return Application.get().getResources().getColor(colorId);
  }

  public static Drawable getDrawable(int drawableId) {
    if (drawableId <= 0) {
      return null;
    }
    return Application.get().getResources().getDrawable(drawableId);
  }
}
