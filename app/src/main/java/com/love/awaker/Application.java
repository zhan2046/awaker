package com.love.awaker;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class Application extends android.app.Application {

  private static Application INSTANCE;

  public static Application get() {
    return INSTANCE;
  }

  @Override public void onCreate() {
    super.onCreate();
    INSTANCE = this;
  }
}
