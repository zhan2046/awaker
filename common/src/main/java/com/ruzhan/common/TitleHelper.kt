package com.ruzhan.common

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewTreeObserver
import com.ruzhan.lion.util.AnimUtils

object TitleHelper {

    private const val DURATION = 900L

    fun setToolbar(toolbar: Toolbar, activity: Activity?) {
        if (activity is AppCompatActivity) {
            activity.setSupportActionBar(toolbar)
        }
    }

    fun setTitleScaleAnim(titleTv: View) {
        titleTv.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                titleTv.viewTreeObserver.removeOnGlobalLayoutListener(this)
                titleTv.alpha = 0f
                titleTv.scaleX = 0.8f
                titleTv.animate()
                        .alpha(1f)
                        .scaleX(1f)
                        .setDuration(DURATION).interpolator = AnimUtils.getFastOutSlowInInterpolator(titleTv.context)
            }
        })
    }
}