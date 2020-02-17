package com.ruzhan.day.image

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ruzhan.day.R
import kotlinx.android.synthetic.main.day_frag_image_detail.*

class DayImageDetailFragment : DialogFragment() {

    companion object {

        private const val IMAGE_URL = "IMAGE_URL"

        private const val DEFAULT_SCALE = 3.0f
        private const val PHOTO_VIEW_DURATION = 350L
        private const val IMAGE_WIDTH = 1920
        private const val IMAGE_HEIGHT = 1280

        @JvmStatic
        fun newInstance(imageUrl: String): DayImageDetailFragment {
            val args = Bundle()
            args.putString(IMAGE_URL, imageUrl)
            val frag = DayImageDetailFragment()
            frag.arguments = args
            return frag
        }
    }

    private var imageUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,
            android.R.style.Theme_Translucent_NoTitleBar)
        imageUrl = arguments?.getString(IMAGE_URL) ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.day_frag_image_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progressBar.alpha = 0.0f
        progressBar.animate().alpha(1.0f).setDuration(PHOTO_VIEW_DURATION)
            .setStartDelay(50).start()
        photoView.attacher.setScaleLevels(DEFAULT_SCALE, DEFAULT_SCALE + 1.0f,
            DEFAULT_SCALE + 2.0f)
        Glide.with(photoView.context)
            .load(imageUrl)
            .override(IMAGE_WIDTH, IMAGE_HEIGHT)
            .transition(DrawableTransitionOptions.withCrossFade())
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?,
                                          target: Target<Drawable>?,
                                          isFirstResource: Boolean): Boolean {
                    e?.printStackTrace()
                    if (progressBar != null) {
                        progressBar.visibility = View.INVISIBLE
                    }
                    return true
                }

                override fun onResourceReady(resource: Drawable?, model: Any?,
                                             target: Target<Drawable>?, dataSource: DataSource?,
                                             isFirstResource: Boolean): Boolean {
                    if (progressBar != null) {
                        progressBar.visibility = View.INVISIBLE
                    }
                    photoView.setImageDrawable(resource)
                    photoView.alpha = 0.0f
                    photoView.postDelayed({
                        photoView.attacher.setScale(DEFAULT_SCALE, true)
                        photoView.animate().alpha(1.0f).setDuration(PHOTO_VIEW_DURATION).start()
                    }, 50)
                    return true
                }
            })
            .into(photoView)
        photoView.attacher.setOnClickListener {
            handlerDismiss()
        }
    }

    private fun handlerDismiss() {
        photoView.alpha = 1.0f
        photoView.animate().alpha(0.0f)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    dismiss()
                }

                override fun onAnimationCancel(animation: Animator?) {
                    super.onAnimationCancel(animation)
                    dismiss()
                }
            })
            .setDuration(PHOTO_VIEW_DURATION).start()
    }
}