package com.awaker.imageloader


object ImageLoader {

    private var imageLoader: IImageLoader? = null

    fun get(): IImageLoader {
        if (imageLoader == null) {
            synchronized(ImageLoader::class.java) {
                if (imageLoader == null) {
                    imageLoader = GlideImpl()
                }
            }
        }
        return imageLoader!!
    }
}
