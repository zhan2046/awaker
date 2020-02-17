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
import com.ruzhan.day.helper.TitleHelper
import com.google.gson.internal.LinkedTreeMap
import com.ruzhan.day.adapter.DayHomeAdapter
import com.ruzhan.day.widget.ScaleTransitionPagerTitleView
import com.ruzhan.font.FontHelper
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

    private val titleList = ArrayList<String>()
    private val dayHomeAdapter: DayHomeAdapter by lazy {
        DayHomeAdapter(childFragmentManager)
    }
    private val commonNavigator: CommonNavigator by lazy {
        CommonNavigator(activity)
    }
    private val dayHomeModel: DayHomeModel by lazy {
        ViewModelProviders.of(requireActivity()).get(DayHomeModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.day_frag_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        initLiveData()
        dayHomeModel.getDayNewList()
    }

    private fun initLiveData() {
        dayHomeModel.tagMapLiveData.observe(this,
                Observer<LinkedTreeMap<String, String>> { tagMap ->
                    if (tagMap != null && tagMap.isNotEmpty()) {
                        titleList.clear()
                        titleList.addAll(tagMap.keys)
                        dayHomeAdapter.setData(tagMap)
                        commonNavigator.notifyDataSetChanged()
                    }
                })
        dayHomeModel.loadStatusLiveData.observe(this, Observer { isLoadStatus ->
            if (isLoadStatus != null && !isLoadStatus) {
                // do nothing
            }
        })
    }

    private fun initData() {
        titleTv.typeface = FontHelper.get().boldFontTypeface
        titleTv.text = resources.getString(R.string.day_common_title_name)
        TitleHelper.setToolbar(toolbar, activity)
        TitleHelper.setAlphaScaleAnimate(titleTv)
        viewPager.adapter = dayHomeAdapter
        initIndicator()
    }

    private fun initIndicator() {
        commonNavigator.adapter = object : CommonNavigatorAdapter() {

            override fun getTitleView(context: Context, index: Int):
                    IPagerTitleView {
                val simplePagerTitleView = ScaleTransitionPagerTitleView(context)
                simplePagerTitleView.typeface = FontHelper.get().boldFontTypeface
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