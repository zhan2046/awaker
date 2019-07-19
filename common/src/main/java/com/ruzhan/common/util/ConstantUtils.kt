package com.ruzhan.common.util

import com.ruzhan.common.BuildConfig

object ConstantUtils {

    const val TOKEN = "f32b30c2a289bfca2c9857ffc5871ac8"

    private const val BUILD_TYPE_RELEASE = "release"
    private const val BUILD_TYPE_BETA = "beta"
    private const val BUILD_TYPE_DEBUG = "debug"

    const val PAGE_SIZE = 10

    @JvmStatic
    val isReleaseBuild: Boolean
        get() = BuildConfig.BUILD_TYPE == BUILD_TYPE_RELEASE

    @JvmStatic
    val isBetaBuild: Boolean
        get() = BuildConfig.BUILD_TYPE == BUILD_TYPE_BETA

    @JvmStatic
    val isDebugBuild: Boolean
        get() = BuildConfig.BUILD_TYPE == BUILD_TYPE_DEBUG
}
