package com.future.movie.video

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.future.movie.R
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
import android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
import android.os.Build
import android.view.View

class VideoActivity : AppCompatActivity() {

    companion object {

        private const val M3U8_URL = "M3U8_URL"

        @JvmStatic
        fun launch(activity: Activity, m3u8Url: String) {
            val intent = Intent(activity, VideoActivity::class.java)
            intent.putExtra(M3U8_URL, m3u8Url)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.setFormat(PixelFormat.TRANSLUCENT)
        hideSystemNavigationBar()
        setContentView(R.layout.lion_container)
        if (savedInstanceState == null) {
            val m3u8Url = intent.getStringExtra(M3U8_URL) ?: ""
            val videoFragment = VideoFragment.newInstance(m3u8Url)
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, videoFragment, "VideoFragment")
                    .commit()
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun hideSystemNavigationBar() {
        when {
            Build.VERSION.SDK_INT in
                Build.VERSION_CODES.HONEYCOMB_MR1..Build.VERSION_CODES.JELLY_BEAN_MR2 -> {
                val view = this.window.decorView
                view.systemUiVisibility = View.GONE
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT -> {
                val decorView = window.decorView
                val uiOptions = (SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or SYSTEM_UI_FLAG_IMMERSIVE_STICKY or SYSTEM_UI_FLAG_FULLSCREEN)
                decorView.systemUiVisibility = uiOptions
            }
            else -> {
                // do nothing
            }
        }
    }
}