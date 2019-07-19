package com.ruzhan.common

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator

object TitleHelper {

    private const val DURATION = 900L
    private const val START_ALPHA = 0f
    private const val END_ALPHA = 1f
    private const val START_SCALE_X = 0.8f
    private const val END_SCALE_X = 1f

    private var fastOutSlowIn: Interpolator? = null

    fun setToolbar(toolbar: Toolbar, activity: Activity?) {
        if (activity is AppCompatActivity) {
            activity.setSupportActionBar(toolbar)
        }
    }

    fun setAlphaScaleAnimate(tagView: View) {
        tagView.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                tagView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                tagView.alpha = START_ALPHA
                tagView.scaleX = START_SCALE_X
                tagView.animate()
                        .alpha(END_ALPHA)
                        .scaleX(END_SCALE_X)
                        .setDuration(DURATION).interpolator = getFastOutSlowInInterpolator(tagView.context)
            }
        })
    }

    fun getFastOutSlowInInterpolator(context: Context): Interpolator? {
        var fastOutSlowIn = fastOutSlowIn
        if (fastOutSlowIn == null) {
            fastOutSlowIn = AnimationUtils.loadInterpolator(context,
                    android.R.interpolator.fast_out_slow_in)
            this.fastOutSlowIn = fastOutSlowIn
        }
        return fastOutSlowIn
    }
}