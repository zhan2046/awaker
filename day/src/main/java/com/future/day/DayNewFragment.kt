package com.future.day

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.future.common.CommonViewModel
import com.future.day.adapter.DayNewAdapter
import com.future.day.base.OnItemClickListener
import com.future.day.db.entity.DayNew
import com.future.day.helper.OnRefreshHelper
import com.future.day.image.DayImageDetailFragment
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

    private val commonViewModel: CommonViewModel by lazy {
        ViewModelProvider(requireActivity()).get(CommonViewModel::class.java)
    }
    private var tagKey = ""
    private val dayHomeModel: DayHomeModel by lazy {
        ViewModelProvider(requireActivity()).get(DayHomeModel::class.java)
    }
    private val dayViewModel: DayViewModel by lazy {
        ViewModelProvider(this).get(DayViewModel::class.java)
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
        dayNewAdapter.onItemClickListener = object : OnItemClickListener<DayNew> {
            override fun onItemClick(itemView: View, position: Int, bean: DayNew) {
                val bundle = DayImageDetailFragment.createBundle(bean.cover_landscape,
                    bean.cover_thumb)
                commonViewModel.addFragment(CommonViewModel.FRAG_IMAGE_DETAIL, bundle)
            }
        }
    }

    private fun initLiveData() {
        dayViewModel.refreshDayNewLiveData.observe(viewLifecycleOwner, Observer { dayNewList ->
            if (dayNewList != null) {
                progressBar.visibility = View.GONE
                dayNewAdapter.setData(dayNewList)
            }
        })
        dayHomeModel.loadStatusLiveData.observe(viewLifecycleOwner, Observer { isLoadStatus ->
            if (isLoadStatus != null && !isLoadStatus) {
                swipeRefreshLayout.isRefreshing = isLoadStatus
            }
        })
    }
}