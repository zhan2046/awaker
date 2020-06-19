package com.future.movie.detail.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.future.common.CommonViewModel
import com.future.movie.R
import com.future.movie.utils.LionUtils
import com.future.movie.utils.ViewUtils
import kotlinx.android.synthetic.main.lion_frag_image_item_detail.*

class ItemImageDetailFragment : Fragment() {

    companion object {

        private const val IMAGE_URL: String = "IMAGE_URL"

        @JvmStatic
        fun newInstance(imageUrl: String): ItemImageDetailFragment {
            val args = Bundle()
            args.putString(IMAGE_URL, imageUrl)
            val frag = ItemImageDetailFragment()
            frag.arguments = args
            return frag
        }
    }

    private val commonViewModel: CommonViewModel by lazy {
        ViewModelProvider(requireActivity()).get(CommonViewModel::class.java)
    }
    private var imageUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageUrl = arguments?.getString(IMAGE_URL) ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.lion_frag_image_item_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val isGif = imageUrl.contains(LionUtils.GIF_FILE)
        photoView.visibility = if (isGif) View.GONE else View.VISIBLE
        imageView.visibility = if (isGif) View.VISIBLE else View.GONE
        if (isGif) {
            handleGif()
        } else {
            handleImage()
        }
        photoView.setOnClickListener {
            commonViewModel.popBackStack()
        }
        imageView.setOnClickListener {
            commonViewModel.popBackStack()
        }
    }

    private fun handleGif() {
        Glide.with(imageView.context)
            .load(imageUrl)
            .placeholder(ViewUtils.getPlaceholder(requireActivity(), 0))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
        progressBar.visibility = View.INVISIBLE
    }

    private fun handleImage() {
        Glide.with(photoView.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .listener(object : RequestListener<Drawable> {

                override fun onResourceReady(resource: Drawable?, model: Any?,
                                             target: Target<Drawable>?, dataSource: DataSource?,
                                             isFirstResource: Boolean): Boolean {
                    if (photoView != null) {
                        photoView.setImageDrawable(resource)
                        progressBar.visibility = View.INVISIBLE
                    }
                    return true
                }

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?,
                                          isFirstResource: Boolean): Boolean {
                    if (progressBar != null) {
                        progressBar.visibility = View.INVISIBLE
                    }
                    return true
                }
            })
            .into(photoView)
    }
}