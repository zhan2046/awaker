package com.future.media

import android.app.Application
import android.content.ComponentName
import android.support.v4.media.MediaBrowserCompat

class MediaControllerManager private constructor(serviceComponent: ComponentName) {

    companion object {

        private lateinit var APPLICATION: Application

        @Volatile
        private var INSTANCE: MediaControllerManager? = null

        @JvmStatic
        fun init(application: Application) {
            APPLICATION = application
        }

        @JvmStatic
        fun get(): MediaControllerManager = INSTANCE ?: synchronized(this) {
            INSTANCE ?: MediaControllerManager(ComponentName(APPLICATION, MediaService::class.java))
                .also { INSTANCE = it }
        }
    }

    private val mediaBrowserConnection = MediaBrowserConnection(APPLICATION, serviceComponent)
    private val innerMediaController = InnerMediaController()

    val isConnected = mediaBrowserConnection.isConnected
    val playbackState = mediaBrowserConnection.playbackState
    val mediaMetadata = mediaBrowserConnection.mediaMetadata

    init {
        MediaExoPlayerManager.init(APPLICATION)
    }

    fun subscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowserConnection.subscribe(parentId, callback)
    }

    fun unsubscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowserConnection.unsubscribe(parentId, callback)
    }

    fun playVideo(url: String) {
        innerMediaController.playVideo(mediaBrowserConnection.mediaController, url)
    }

    fun pause() {
        innerMediaController.pause(mediaBrowserConnection.mediaController)
    }

    fun stop() {
        innerMediaController.stop(mediaBrowserConnection.mediaController)
    }

}