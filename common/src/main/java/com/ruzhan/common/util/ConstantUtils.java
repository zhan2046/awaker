package com.ruzhan.common.util;

import com.ruzhan.common.BuildConfig;

public final class ConstantUtils {

    public static final String TOKEN = "f32b30c2a289bfca2c9857ffc5871ac8";

    public static final String BUILD_TYPE_RELEASE = "release";
    public static final String BUILD_TYPE_BETA = "beta";
    public static final String BUILD_TYPE_DEBUG = "debug";

    public static final int PAGE_SIZE = 10;

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
