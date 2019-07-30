package com.ruzhan.day

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lion.font.FontHelper
import com.ruzhan.common.OnRefreshHelper
import com.ruzhan.common.TitleHelper
import com.ruzhan.day.adapter.DayNewAdapter
import kotlinx.android.synthetic.main.day_frag_new.*

class DayNewFragment : androidx.fragment.app.Fragment() {

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
        titleTv.text = resources.getString(R.string.day_common_title_name)
        TitleHelper.setToolbar(toolbar, activity)
        recyclerView.adapter = dayNewAdapter

        initLiveData()
        initListener()
        dayViewModel.getLocalDayList()
        dayViewModel.refreshDayNewList()
    }

    private fun initListener() {
        TitleHelper.setAlphaScaleAnimate(titleTv)
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
                progressBar.visibility = View.GONE
                dayNewAdapter.setRefreshData(dayNewList)
            }
        })
        dayViewModel.loadMoreDayNewLiveData.observe(this, Observer { dayNewList ->
            if (dayNewList != null) {
                progressBar.visibility = View.GONE
                dayNewAdapter.setLoadMoreData(dayNewList)
            }
        })
        dayViewModel.loadStatusLiveData.observe(this, Observer { isLoadStatus ->
            if (isLoadStatus != null && !isLoadStatus) {
                swipeRefreshLayout.isRefreshing = isLoadStatus
            }
        })
    }
}