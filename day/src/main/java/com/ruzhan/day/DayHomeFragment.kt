package com.ruzhan.day

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lion.font.FontHelper
import com.ruzhan.common.TitleHelper
import com.ruzhan.day.adapter.DayHomeAdapter
import com.ruzhan.day.widget.ScaleTransitionPagerTitleView
import kotlinx.android.synthetic.main.day_frag_home.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

class DayHomeFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() = DayHomeFragment()
    }

    private var dayHomeAdapter: DayHomeAdapter? = null
    private var commonNavigator: CommonNavigator? = null
    private val titleList = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.day_frag_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dayViewModel = ViewModelProviders.of(activity!!)
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
                        titleList.clear()
                        titleList.addAll(tagList)
                        dayHomeAdapter?.setData(titleList)
                        commonNavigator?.notifyDataSetChanged()
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
        initIndicator()
    }

    private fun initIndicator() {
        val commonNavigator = CommonNavigator(activity)
        this.commonNavigator = commonNavigator
        commonNavigator.adapter = object : CommonNavigatorAdapter() {

            override fun getTitleView(context: Context, index: Int):
                    IPagerTitleView {
                val simplePagerTitleView = ScaleTransitionPagerTitleView(context)
                simplePagerTitleView.typeface = FontHelper.get().getBoldTypeface()
                simplePagerTitleView.text = titleList[index]
                simplePagerTitleView.textSize = 15f
                simplePagerTitleView.normalColor =
                        ContextCompat.getColor(context, R.color.text_secondary_dark)
                simplePagerTitleView.selectedColor =
                        ContextCompat.getColor(context, R.color.colorAccent)
                simplePagerTitleView.setOnClickListener {
                    viewPager.currentItem = index
                }
                return simplePagerTitleView
            }

            override fun getCount(): Int = titleList.size

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                indicator.setColors(ContextCompat.getColor(context, R.color.colorAccent))
                indicator.roundRadius = resources.getDimension(R.dimen.space_small_6)
                indicator.xOffset = resources.getDimension(R.dimen.space_small_2)
                indicator.yOffset = resources.getDimension(R.dimen.space_small_8)
                indicator.lineHeight = resources.getDimension(R.dimen.space_small_2)
                return indicator
            }
        }
        magicIndicator.navigator = commonNavigator
        ViewPagerHelper.bind(magicIndicator, viewPager)
    }
}