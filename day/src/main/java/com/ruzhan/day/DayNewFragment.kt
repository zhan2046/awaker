package com.ruzhan.day

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ruzhan.day.helper.OnRefreshHelper
import com.ruzhan.day.adapter.DayNewAdapter
import kotlinx.android.synthetic.main.day_frag_new.*

class DayNewFragment : Fragment() {

    companion object {

        private const val TAG_KEY = "TAG_KEY"

        @JvmStatic
        fun newInstance(tagKey: String): DayNewFragment {
            val args = Bundle()
            args.putString(TAG_KEY, tagKey)
            val frag = DayNewFragment()
            frag.arguments = args
            return frag
        }
    }

    private var tagKey = ""
    private val dayHomeModel: DayHomeModel by lazy {
        ViewModelProviders.of(activity!!).get(DayHomeModel::class.java)
    }
    private val dayViewModel: DayViewModel by lazy {
        ViewModelProviders.of(this).get(DayViewModel::class.java)
    }
    private val dayNewAdapter: DayNewAdapter by lazy {
        DayNewAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tagKey = arguments?.getString(TAG_KEY, "") ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.day_frag_new, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        initListener()
        initLiveData()
        dayViewModel.loadDayNewList(tagKey)
    }

    private fun initData() {
        recyclerView.adapter = dayNewAdapter
    }

    private fun initListener() {
        OnRefreshHelper.setOnRefreshStatusListener(swipeRefreshLayout, recyclerView,
                object : OnRefreshHelper.OnRefreshStatusListener {

                    override fun onRefresh() {
                        dayHomeModel.getDayNewList()
                    }

                    override fun onLoadMore() {
                        // do nothing
                    }
                })
    }

    private fun initLiveData() {
        dayViewModel.refreshDayNewLiveData.observe(this, Observer { dayNewList ->
            if (dayNewList != null) {
                progressBar.visibility = View.GONE
                dayNewAdapter.setData(dayNewList)
            }
        })
        dayHomeModel.loadStatusLiveData.observe(this, Observer { isLoadStatus ->
            if (isLoadStatus != null && !isLoadStatus) {
                swipeRefreshLayout.isRefreshing = isLoadStatus
            }
        })
    }
}