package com.ruzhan.day.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ruzhan.day.DayNewFragment

class DayHomeAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private var titleList = ArrayList<String>()

    fun setData(list: List<String>) {
        titleList.clear()
        titleList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        val dayTag = titleList[position]
        return DayNewFragment.newInstance(dayTag)
    }

    override fun getCount(): Int = titleList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }
}