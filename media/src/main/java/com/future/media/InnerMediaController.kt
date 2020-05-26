package com.future.media

import android.os.Bundle
import android.support.v4.media.session.MediaControllerCompat
import com.future.media.model.MediaDataModel

class InnerMediaController {

    fun playVideo(mediaController: MediaControllerCompat?, url: String) {
        handleMediaPlay(mediaController, url, MediaDataModel.VIDEO)
    }

    fun playAudio(mediaController: MediaControllerCompat?, url: String) {
        handleMediaPlay(mediaController, url, MediaDataModel.AUDIO)
    }

    private fun handleMediaPlay(mediaController: MediaControllerCompat?,
                                url: String, type: String) {
        val mediaList = createMediaDataModelList(url, type)
        val extras = createMediaExtras(type, mediaList)
        mediaController?.transportControls?.prepareFromMediaId(url, extras)
    }

    private fun createMediaExtras(type: String,
                                  mediaList: ArrayList<MediaDataModel>): Bundle {
        val extras = Bundle()
        extras.putString(MediaDataModel.MEDIA_TYPE, type)
        extras.putParcelableArrayList(MediaDataModel.MEDIA_LIST, mediaList)
        return extras
    }

    private fun createMediaDataModelList(url: String, type: String): ArrayList<MediaDataModel> {
        val mediaList = ArrayList<MediaDataModel>()
        val videoModel = MediaDataModel()
        videoModel.type = type
        videoModel.url = url
        mediaList.add(videoModel)
        return mediaList
    }

    fun pause(mediaController: MediaControllerCompat?) {
        mediaController?.transportControls?.pause()
    }

    fun stop(mediaController: MediaControllerCompat?) {
        mediaController?.transportControls?.stop()
    }
}