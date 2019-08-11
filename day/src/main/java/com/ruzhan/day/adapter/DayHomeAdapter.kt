package com.ruzhan.day.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ruzhan.common.util.ResUtils
import com.ruzhan.day.DayNewFragment
import com.ruzhan.day.R

class DayHomeAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private var titleList = ArrayList<String>()
    private val newListTab = ResUtils.getString(R.string.day_new_list_tab)
    private var newListTabPosition = 0

    fun setData(list: List<String>) {
        titleList.clear()
        titleList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        var dayTag = titleList[position]
        if (newListTab == dayTag) {
            newListTabPosition = position
        }
        if (position == 0) {
            dayTag = newListTab
            newListTabPosition = titleList.indexOf(newListTab)
        } else if (position == newListTabPosition) {
            dayTag = titleList[0]
        }
        return DayNewFragment.newInstance(dayTag)
    }

    override fun getCount(): Int = titleList.size

    override fun getPageTitle(position: Int): CharSequence? {
        var title = titleList[position]
        if (position == 0) {
            title = newListTab
            newListTabPosition = titleList.indexOf(newListTab)
        } else if (position == newListTabPosition) {
            title = titleList[0]
        }
        return title
    }
}