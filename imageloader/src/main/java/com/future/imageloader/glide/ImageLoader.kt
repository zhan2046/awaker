package com.future.imageloader.glide

class ImageLoader {

    companion object {

        @Volatile
        private var INSTANCE: IImageLoader? = null

        @JvmStatic
        fun get(): IImageLoader = INSTANCE ?: synchronized(ImageLoader::class) {
            INSTANCE ?: GlideImpl().also { INSTANCE = it }
        }
    }
}