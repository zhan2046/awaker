package com.awaker.imageloader


object ImageLoader {

    private var imageLoader: IImageLoader? = null

    fun get(): IImageLoader = imageLoader ?: synchronized(ImageLoader::class.java) {
        imageLoader ?: GlideImpl().also { imageLoader = it }
    }
}
