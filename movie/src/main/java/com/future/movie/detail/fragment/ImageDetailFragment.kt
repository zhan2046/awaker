package com.future.movie.detail.fragment

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.ruzhan.font.FontHelper
import com.future.movie.R
import com.future.movie.detail.adapter.ImageDetailAdapter
import com.future.movie.model.ImageListModel
import kotlinx.android.synthetic.main.lion_frag_image_detail.*


class ImageDetailFragment : Fragment() {

    companion object {

        private const val IMAGE_LIST_MODEL: String = "IMAGE_LIST_MODEL"

        @JvmStatic
        fun newInstance(imageListModel: ImageListModel): ImageDetailFragment {
            val args = Bundle()
            args.putSerializable(IMAGE_LIST_MODEL, imageListModel)
            val frag = ImageDetailFragment()
            frag.arguments = args
            return frag
        }
    }

    private lateinit var imageListModel: ImageListModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageListModel = arguments?.getSerializable(IMAGE_LIST_MODEL) as ImageListModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.lion_frag_image_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        initListener()
    }

    private fun initListener() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
                // do nothing
            }

            override fun onPageScrolled(position: Int, positionOffset: Float,
                                        positionOffsetPixels: Int) {
                // do nothing
            }

            override fun onPageSelected(position: Int) {
                val currentPositionText = (position + 1).toString()
                resetBottomContentText(currentPositionText)
            }
        })
    }

    private fun initData() {
        val currentPositionText = (imageListModel.position + 1).toString()
        resetBottomContentText(currentPositionText)

        titleTv.text = imageListModel.title
        bottomContentTv.typeface = FontHelper.get().boldFontTypeface
        titleTv.typeface = FontHelper.get().boldFontTypeface
        val imageDetailAdapter = ImageDetailAdapter(childFragmentManager,
            imageListModel.imageList)
        viewPager.adapter = imageDetailAdapter
        viewPager.currentItem = imageListModel.position
    }

    private fun resetBottomContentText(currentPositionText: String) {
        val centerText = " / "
        val totalPositionText = imageListModel.imageList.size.toString()
        val imageBottomText = currentPositionText
                .plus(centerText)
                .plus(totalPositionText)
        val spannableString = SpannableString(imageBottomText)
        val currentColorSpan = ForegroundColorSpan(ContextCompat.getColor(activity!!,
                R.color.colorAccent))
        spannableString.setSpan(currentColorSpan, 0, currentPositionText.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        val totalColorSpan = ForegroundColorSpan(ContextCompat.getColor(activity!!,
                R.color.light_grey))
        spannableString.setSpan(totalColorSpan, currentPositionText.length, imageBottomText.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(AbsoluteSizeSpan(18, true),
                0, currentPositionText.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        spannableString.setSpan(AbsoluteSizeSpan(22, true),
                currentPositionText.length, imageBottomText.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        bottomContentTv.text = spannableString
    }
}