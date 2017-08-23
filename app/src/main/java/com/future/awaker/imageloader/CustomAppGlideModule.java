package com.future.awaker.imageloader;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Copyright ©2017 by ruzhan
 */
@GlideModule
public class CustomAppGlideModule extends AppGlideModule {

    /**
     * 设置内存缓存大小10M
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setMemoryCache(new LruResourceCache(20 * 1024 * 1024));
    }

    /**
     * 关闭解析AndroidManifest
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
