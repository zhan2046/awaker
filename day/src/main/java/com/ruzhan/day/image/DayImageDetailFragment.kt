package com.ruzhan.day.image

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ruzhan.day.R
import kotlinx.android.synthetic.main.day_frag_image_detail.*

class DayImageDetailFragment : Fragment() {

    companion object {

        private const val IMAGE_URL = "IMAGE_URL"

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
        imageUrl = arguments?.getString(IMAGE_URL) ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.day_frag_image_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        photoView.attacher.setScaleLevels(3.0f, 4.0f, 6.0f)
        Glide.with(photoView.context)
            .load(imageUrl)
            .override(1920, 1280)
            .transition(DrawableTransitionOptions.withCrossFade())
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    e?.printStackTrace()
                    if (progressBar != null) {
                        progressBar.visibility = View.INVISIBLE
                    }
                    return true
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    if (progressBar != null) {
                        progressBar.visibility = View.INVISIBLE
                    }
                    photoView.setImageDrawable(resource)
                    photoView.alpha = 0.0f
                    photoView.postDelayed({
                        photoView.attacher.setScale(3.0f, true)
                        photoView.animate().alpha(1.0f).setDuration(500).start()
                    }, 50)
                    return true
                }
            })
            .into(photoView)
    }
}