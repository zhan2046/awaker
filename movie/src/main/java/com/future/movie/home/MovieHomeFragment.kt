package com.future.movie.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ruzhan.font.FontHelper
import com.future.movie.R
import com.future.movie.home.adapter.MovieHomeAdapter
import com.future.movie.home.viewmodel.MovieHomeViewModel
import com.future.movie.utils.LionTitleHelper
import com.future.movie.widget.ScaleTransitionPagerTitleView
import kotlinx.android.synthetic.main.lion_movie_main_home.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

class MovieHomeFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() = MovieHomeFragment()
    }

    private val titleList = ArrayList<String>()
    private val movieHomeAdapter: MovieHomeAdapter by lazy {
        MovieHomeAdapter(childFragmentManager)
    }
    private val commonNavigator: CommonNavigator by lazy {
        CommonNavigator(activity)
    }
    private val movieHomeViewModel: MovieHomeViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(MovieHomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.lion_movie_main_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        initLiveData()
        movieHomeViewModel.getMovieList()
    }

    private fun initLiveData() {
        movieHomeViewModel.titleListLiveData.observe(viewLifecycleOwner,
            Observer<List<String>> { tagList ->
                tagList?.let {
                    titleList.clear()
                    titleList.addAll(tagList)
                    movieHomeAdapter.setData(titleList)
                    commonNavigator.notifyDataSetChanged()
                }
            })
    }

    private fun initData() {
        titleTv.typeface = FontHelper.get().boldFontTypeface
        titleTv.text = resources.getString(R.string.lion_tab_movie_title)
        LionTitleHelper.setAlphaScaleAnimate(titleTv)
        viewPager.adapter = movieHomeAdapter
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
                indicator.lineHeight = resources.getDimension(R.dimen.indicator_height)
                return indicator
            }
        }
        magicIndicator.navigator = commonNavigator
        ViewPagerHelper.bind(magicIndicator, viewPager)
    }
}