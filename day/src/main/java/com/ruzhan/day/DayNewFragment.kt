package com.ruzhan.day

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ruzhan.common.TitleHelper
import com.ruzhan.day.adapter.DayNewAdapter
import com.ruzhan.lion.helper.FontHelper
import com.ruzhan.lion.helper.OnRefreshHelper
import kotlinx.android.synthetic.main.day_frag_new.*

class DayNewFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() = DayNewFragment()
    }

    private lateinit var dayViewModel: DayViewModel
    private val dayNewAdapter = DayNewAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.day_frag_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dayViewModel = ViewModelProviders.of(this).get(DayViewModel::class.java)
        this.dayViewModel = dayViewModel

        titleTv.typeface = FontHelper.get().getBoldTypeface()
        titleTv.text = resources.getString(R.string.common_title_name)
        TitleHelper.setToolbar(toolbar, activity)

        recyclerView.adapter = dayNewAdapter

        initLiveData()
        initListener()
        dayViewModel.refreshDayNewList()
    }

    private fun initListener() {
        TitleHelper.setTitleScaleAnim(titleTv)
        OnRefreshHelper.setOnRefreshStatusListener(swipeRefreshLayout, recyclerView,
                object : OnRefreshHelper.OnRefreshStatusListener {
                    override fun onLoadMore() {
                        dayViewModel.refreshDayNewList()
                    }

                    override fun onRefresh() {
                        dayViewModel.loadMoreDayNewList()
                    }
                })
    }

    private fun initLiveData() {
        dayViewModel.refreshDayNewLiveData.observe(this, Observer { dayNewList ->
            if (dayNewList != null) {
                dayNewAdapter.setRefreshData(dayNewList)
            }
        })
        dayViewModel.loadMoreDayNewLiveData.observe(this, Observer { dayNewList ->
            if (dayNewList != null) {
                dayNewAdapter.setLoadMoreData(dayNewList)
            }
        })
        dayViewModel.loadStatusLiveData.observe(this, Observer { isLoadStatus ->
            if (isLoadStatus != null) {
                swipeRefreshLayout.isRefreshing = isLoadStatus
            }
        })
    }
}