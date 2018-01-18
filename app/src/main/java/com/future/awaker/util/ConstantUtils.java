package com.future.awaker.util;

import com.future.awaker.BuildConfig;



public final class ConstantUtils {

    public static final String BUILD_TYPE_RELEASE = "release";
    public static final String BUILD_TYPE_BETA = "beta";
    public static final String BUILD_TYPE_DEBUG = "debug";

    public static boolean isReleaseBuild() {
        return BuildConfig.BUILD_TYPE.equals(BUILD_TYPE_RELEASE);
    }

    public static boolean isBetaBuild() {
        return BuildConfig.BUILD_TYPE.equals(BUILD_TYPE_BETA);
    }

    public static boolean isDebugBuild() {
        return BuildConfig.BUILD_TYPE.equals(BUILD_TYPE_DEBUG);
    }
}
