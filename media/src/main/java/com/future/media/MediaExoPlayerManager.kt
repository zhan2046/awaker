package com.future.media

import android.app.Application
import android.content.Context
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes

class MediaExoPlayerManager private constructor() {

    companion object {

        private lateinit var APPLICATION: Application

        @Volatile
        private var INSTANCE: MediaExoPlayerManager? = null

        @JvmStatic
        fun init(application: Application) {
            APPLICATION = application
        }

        @JvmStatic
        fun get() = INSTANCE ?: synchronized(this) {
            INSTANCE ?: MediaExoPlayerManager().also { INSTANCE = it }
        }
    }

    private val uAmpAudioAttributes = AudioAttributes.Builder()
        .setContentType(C.CONTENT_TYPE_MUSIC)
        .setUsage(C.USAGE_MEDIA)
        .build()

    val exoPlayer: SimpleExoPlayer by lazy {
        createSimpleExoPlayer(APPLICATION).also {
            it.setAudioAttributes(uAmpAudioAttributes, true)
        }
    }

    private fun createSimpleExoPlayer(context: Context): SimpleExoPlayer {
        val defaultRenderersFactory = DefaultRenderersFactory(context)
        defaultRenderersFactory.setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
        return SimpleExoPlayer.Builder(context, defaultRenderersFactory).build()
    }
}