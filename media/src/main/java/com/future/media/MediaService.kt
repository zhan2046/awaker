package com.future.media

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.MediaBrowserServiceCompat
import androidx.media.session.MediaButtonReceiver
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.Util
import java.io.File

class MediaService : MediaBrowserServiceCompat() {

    companion object {

        const val TAG = "MediaService"
        const val USER_AGENT = "media.user"
        const val BROWSE_ROOT = "/"
        const val CACHE_DIR_NAME = "media"
        const val CACHE_MEDIA_MAX_BYTES = 300 * 1024 * 1024L

        private var SIMPLE_CACHE: SimpleCache? = null
    }

    private val mediaSession: MediaSessionCompat by lazy {
        MediaSessionCompat(this, TAG).also {
            initMediaSession(it)
        }
    }
    private val mediaSessionConnector: MediaSessionConnector by lazy {
        MediaSessionConnector(mediaSession)
    }
    private val exoPlayer: SimpleExoPlayer by lazy {
        MediaExoPlayerManager.get().exoPlayer
    }
    private val mediaPlaybackPreparer: MediaPlaybackPreparer by lazy {
        MediaPlaybackPreparer(this, exoPlayer, getCacheDataSourceFactory())
    }
    private val becomingNoisyReceiver: BecomingNoisyReceiver by lazy {
        BecomingNoisyReceiver(this, sessionToken!!)
    }

    override fun onLoadChildren(parentId: String,
                                result: Result<MutableList<MediaBrowserCompat.MediaItem>>) {
        result.sendResult(ArrayList())
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int,
                           rootHints: Bundle?): BrowserRoot? {
        return BrowserRoot(BROWSE_ROOT, null)
    }

    private fun initMediaSession(mediaSession: MediaSessionCompat) {
        val sessionIntent = packageManager?.getLaunchIntentForPackage(packageName)
        val sessionActivityPendingIntent = PendingIntent.getActivity(this,
            0, sessionIntent, 0)
        mediaSession.setSessionActivity(sessionActivityPendingIntent)
        mediaSession.isActive = true
        sessionToken = mediaSession.sessionToken
        becomingNoisyReceiver.register()
    }

    override fun onCreate() {
        super.onCreate()
        mediaSessionConnector.setPlayer(exoPlayer)
        mediaSessionConnector.setPlaybackPreparer(mediaPlaybackPreparer)
        mediaSessionConnector.setQueueNavigator(MediaQueueNavigator(mediaSession))
    }

    private fun getCacheDataSourceFactory(): CacheDataSourceFactory {
        val defaultHttpDataSourceFactory =
            DefaultHttpDataSourceFactory(Util.getUserAgent(this, USER_AGENT),
                null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true)
        val dataSourceFactory = DefaultDataSourceFactory(this, defaultHttpDataSourceFactory)
        if (SIMPLE_CACHE == null) {
            SIMPLE_CACHE = getSimpleCache()
        }
        return CacheDataSourceFactory(SIMPLE_CACHE, dataSourceFactory)
    }

    private fun getSimpleCache(): SimpleCache {
        val cacheFolder = File(cacheDir, CACHE_DIR_NAME)
        val leastRecentlyUsedCache = LeastRecentlyUsedCacheEvictor(CACHE_MEDIA_MAX_BYTES)
        return SimpleCache(cacheFolder, leastRecentlyUsedCache,
            null, null, false, true)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        MediaButtonReceiver.handleIntent(mediaSession, intent)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        super.onTaskRemoved(rootIntent)
        exoPlayer.stop(true)
    }

    override fun onDestroy() {
        mediaSession.isActive = false
        mediaSession.release()
        becomingNoisyReceiver.unregister()
    }
}
