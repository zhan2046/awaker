package com.future.movie.video

import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.future.media.MediaControllerManager
import com.future.media.MediaExoPlayerManager
import com.future.movie.R
import kotlinx.android.synthetic.main.lion_frag_video.*

class VideoFragment : Fragment() {

    companion object {

        private const val M3U8_URL = "M3U8_URL"

        @JvmStatic
        fun newInstance(m3u8Url: String): VideoFragment {
            val args = Bundle()
            args.putString(M3U8_URL, m3u8Url)
            val frag = VideoFragment()
            frag.arguments = args
            return frag
        }
    }

    private var m3u8Url = ""
    private val mediaControllerManager = MediaControllerManager.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        m3u8Url = arguments?.getString(M3U8_URL) ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.lion_frag_video, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        playerView.player = MediaExoPlayerManager.get().exoPlayer
        mediaControllerManager.playVideo(m3u8Url)
        mediaControllerManager.playbackState.observe(viewLifecycleOwner, Observer {
            handlePlaybackState(it)
        })
    }

    private fun handlePlaybackState(playbackState: PlaybackStateCompat?) {
        if (playbackState != null) {
            when (playbackState.state) {
                PlaybackStateCompat.STATE_BUFFERING -> {
                    if (progressBar.visibility != View.VISIBLE) {
                        progressBar.visibility = View.VISIBLE
                    }
                }
                else -> {
                    if (progressBar.visibility != View.GONE) {
                        progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mediaControllerManager.pause()
    }

    override fun onDestroyView() {
        mediaControllerManager.stop()
        super.onDestroyView()
    }
}