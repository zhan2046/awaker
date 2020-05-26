package com.future.media

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.future.media.model.MediaDataModel
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

class MediaPlaybackPreparer(private val context: Context,
                            private val exoPlayer: SimpleExoPlayer,
                            private val dataSourceFactory: DataSource.Factory
) : MediaSessionConnector.PlaybackPreparer {

    override fun onPrepareFromSearch(query: String, playWhenReady: Boolean, extras: Bundle) {
        // do nothing
    }

    override fun onCommand(player: Player, controlDispatcher: ControlDispatcher, command: String,
                           extras: Bundle, cb: ResultReceiver): Boolean {
        when (command) {
            "" -> {

            }
            else -> {
                // do nothing
            }
        }
        return true
    }

    override fun getSupportedPrepareActions(): Long =
        PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID or
            PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID or
            PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH or
            PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH

    override fun onPrepareFromMediaId(mediaId: String, playWhenReady: Boolean, extras: Bundle) {
        when (extras.getString(MediaDataModel.MEDIA_TYPE)) {
            MediaDataModel.VIDEO -> {
                val mediaList =
                    extras.getParcelableArrayList<MediaDataModel>(MediaDataModel.MEDIA_LIST)
                if (mediaList != null && mediaList.isNotEmpty()) {
                    val packageName = context.applicationInfo.packageName
                    val concatenatingMediaSource =
                        createConcatenatingMediaSource(mediaList, packageName)
                    exoPlayer.prepare(concatenatingMediaSource)
                    exoPlayer.playWhenReady = true
                }
            }
            MediaDataModel.AUDIO -> {

            }
            else -> {
                // do nothing
            }
        }
    }

    private fun createConcatenatingMediaSource(mediaList: ArrayList<MediaDataModel>,
                                               packageName: String): ConcatenatingMediaSource {
        val concatenatingMediaSource = ConcatenatingMediaSource()
        for (mediaDataModel in mediaList) {
            val mediaUrl = mediaDataModel.url
            val videoUri = Uri.parse(mediaUrl)
            val mediaMetadata = MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, mediaDataModel.getMediaId())
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, mediaDataModel.getMediaName())
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, mediaDataModel.getMediaUrl())
                .build()
            mediaMetadata.description.extras?.putAll(mediaMetadata.bundle)
            val hlsMediaSource =
                HlsMediaSource.Factory(DefaultHttpDataSourceFactory(packageName))
                    .setTag(mediaMetadata.description)
                    .createMediaSource(videoUri)
            concatenatingMediaSource.addMediaSource(hlsMediaSource)
        }
        return concatenatingMediaSource
    }

    override fun onPrepareFromUri(uri: Uri, playWhenReady: Boolean, extras: Bundle) {
        // do nothing
    }

    override fun onPrepare(playWhenReady: Boolean) {
        // do nothing
    }
}