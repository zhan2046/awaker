package com.future.movie.detail.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.future.movie.detail.fragment.ItemImageDetailFragment

class ImageDetailAdapter(fm: FragmentManager, imageList: ArrayList<String>) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var imageList: List<String> = imageList

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        val imageUrl = imageList[position]
        return ItemImageDetailFragment.newInstance(imageUrl)
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return imageList[position]
    }
}