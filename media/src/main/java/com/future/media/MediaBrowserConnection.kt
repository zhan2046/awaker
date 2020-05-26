package com.future.media

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.MutableLiveData

class MediaBrowserConnection(context: Context, serviceComponent: ComponentName) {

    private val mediaBrowserConnectionCallback = MediaBrowserConnectionCallback(context)
    private val mediaBrowser = MediaBrowserCompat(context, serviceComponent,
        mediaBrowserConnectionCallback, null)

    var mediaController: MediaControllerCompat? = null
    val isConnected = MutableLiveData<Boolean>()
    val playbackState = MutableLiveData<PlaybackStateCompat>()
    val mediaMetadata = MutableLiveData<MediaMetadataCompat>()

    init {
        isConnected.value = false
        mediaBrowser.connect()
    }

    private inner class MediaBrowserConnectionCallback(private val context: Context)
        : MediaBrowserCompat.ConnectionCallback() {

        override fun onConnected() {
            val mediaController = MediaControllerCompat(context, mediaBrowser.sessionToken)
            this@MediaBrowserConnection.mediaController = mediaController
            mediaController.registerCallback(MediaControllerCallback())
            isConnected.value = true
        }

        override fun onConnectionSuspended() {
            isConnected.value = false
        }

        override fun onConnectionFailed() {
            isConnected.value = false
        }
    }

    private inner class MediaControllerCallback : MediaControllerCompat.Callback() {

        override fun onSessionEvent(event: String?, extras: Bundle?) {
            // do nothing
        }

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            playbackState.value = state
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            mediaMetadata.value = metadata
        }

        override fun onQueueChanged(queue: MutableList<MediaSessionCompat.QueueItem>?) {
            // do nothing
        }

        override fun onSessionDestroyed() {
            mediaBrowserConnectionCallback.onConnectionSuspended()
        }
    }

    fun subscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.subscribe(parentId, callback)
    }

    fun unsubscribe(parentId: String, callback: MediaBrowserCompat.SubscriptionCallback) {
        mediaBrowser.unsubscribe(parentId, callback)
    }
}