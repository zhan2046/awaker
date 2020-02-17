package com.ruzhan.day.image

import android.app.Activity
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
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
        setContentView(R.layout.day_container)
        val imageUrl = intent?.getStringExtra(IMAGE_URL) ?: ""
        val frag = DayImageDetailFragment.newInstance(imageUrl)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, frag, "DayImageDetailFragment")
            .commit()
    }
}