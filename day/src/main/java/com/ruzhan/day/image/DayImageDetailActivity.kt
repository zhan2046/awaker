package com.ruzhan.day.image

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.ruzhan.day.R

class DayImageDetailActivity : AppCompatActivity() {

    companion object {

        private const val IMAGE_URL = "IMAGE_URL"

        @JvmStatic
        fun launch(activity: Activity, imageUrl: String) {
            val intent = Intent(activity, DayImageDetailActivity::class.java)
            intent.putExtra(IMAGE_URL, imageUrl)
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
        setContentView(R.layout.day_container)
        val imageUrl = intent?.getStringExtra(IMAGE_URL) ?: ""
        val frag = DayImageDetailFragment.newInstance(imageUrl)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, frag, "DayImageDetailFragment")
            .commit()
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun hideSystemNavigationBar() {
        if (Build.VERSION.SDK_INT in 12..18) {
            val view = this.window.decorView
            view.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            val decorView = window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
            decorView.systemUiVisibility = uiOptions
        }
    }
}