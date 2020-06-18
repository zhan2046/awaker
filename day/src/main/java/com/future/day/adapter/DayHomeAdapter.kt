package com.future.day.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.future.day.DayNewFragment
import com.google.gson.internal.LinkedTreeMap

class DayHomeAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var tagMap: LinkedTreeMap<String, String>? = null
    private val titleList = ArrayList<String>()

    fun setData(map: LinkedTreeMap<String, String>) {
        tagMap = map
        titleList.clear()
        titleList.addAll(map.keys)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return DayNewFragment.newInstance(getTagKey(position))
    }

    override fun getCount(): Int = tagMap?.size ?: 0

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    private fun getTagKey(position: Int): String {
        var tagKey = ""
        val tagMap = tagMap
        if (tagMap != null) {
            val keys = ArrayList(tagMap.keys)
            tagKey = tagMap.getValue(keys[position])
        }
        return tagKey
    }
}