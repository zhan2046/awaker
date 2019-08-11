package com.ruzhan.day

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lion.font.FontHelper
import com.ruzhan.common.TitleHelper
import com.ruzhan.day.adapter.DayHomeAdapter
import kotlinx.android.synthetic.main.day_frag_home.*

class DayHomeFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() = DayHomeFragment()
    }

    private var dayHomeAdapter: DayHomeAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.day_frag_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dayViewModel = ViewModelProviders.of(this)
                .get(DayViewModel::class.java)
        initData()
        initLiveData(dayViewModel)
        dayViewModel.getLocalDayList()
        dayViewModel.refreshDayNewList()
    }

    private fun initLiveData(dayViewModel: DayViewModel) {
        dayViewModel.tagListLiveData.observe(this,
                Observer<List<String>> { tagList ->
                    if (tagList != null) {
                        dayHomeAdapter?.setData(tagList)
                    }
                })
    }

    private fun initData() {
        titleTv.typeface = FontHelper.get().getBoldTypeface()
        titleTv.text = resources.getString(R.string.day_common_title_name)
        TitleHelper.setToolbar(toolbar, activity)
        TitleHelper.setAlphaScaleAnimate(titleTv)

        val dayHomeAdapter = DayHomeAdapter(childFragmentManager)
        this.dayHomeAdapter = dayHomeAdapter
        viewPager.adapter = dayHomeAdapter
        tabLayout.setupWithViewPager(viewPager)
    }
}