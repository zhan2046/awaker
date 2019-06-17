package com.ruzhan.awaker.article

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ruzhan.common.util.ResUtils
import com.ruzhan.lion.helper.FontHelper
import kotlinx.android.synthetic.main.awaker_article_frag_home.*

class ArticleHomeFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() = ArticleHomeFragment()
    }

    private lateinit var articleHomeAdapter: ArticleHomeAdapter

    private var currentPosition: Int = 0
    private var onFragmentLoadListener: OnFragmentLoadListener? = null

    private val fragmentLoadTask: FragmentLoadTask = FragmentLoadTask()
    private val handler: Handler = Handler(Looper.getMainLooper())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.awaker_article_frag_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title_tv.typeface = FontHelper.get().getBoldTypeface()
        title_tv.text = resources.getString(R.string.awaker_article_news_title)
        setToolbar(toolbar)

        val titleList = getTitleList()

        articleHomeAdapter = ArticleHomeAdapter(childFragmentManager, titleList)
        view_pager.adapter = articleHomeAdapter

        tabs.tabMode = android.support.design.widget.TabLayout.MODE_SCROLLABLE
        tabs.setupWithViewPager(view_pager)

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                handler.removeCallbacks(fragmentLoadTask)
                onFragmentLoadListener = articleHomeAdapter.getOnFragmentLoadListener(position)
                handler.postDelayed(fragmentLoadTask, 600)
            }
        })
    }

    private fun getTitleList(): List<String> {
        val titleArr = ArrayList<String>()
        titleArr.add(ResUtils.getString(R.string.awaker_article_all))
        titleArr.add(ResUtils.getString(R.string.awaker_article_week_hot_read))
        titleArr.add(ResUtils.getString(R.string.awaker_article_week_hot_write))
        titleArr.add(ResUtils.getString(R.string.awaker_article_spiritual_awakening))
        titleArr.add(ResUtils.getString(R.string.awaker_article_scientific_exploration))
        titleArr.add(ResUtils.getString(R.string.awaker_article_ufo))
        titleArr.add(ResUtils.getString(R.string.awaker_article_free_energy))
        titleArr.add(ResUtils.getString(R.string.awaker_article_unsolved_puzzle))
        titleArr.add(ResUtils.getString(R.string.awaker_article_crop_circles))
        titleArr.add(ResUtils.getString(R.string.awaker_article_reverse_gene))
        titleArr.add(ResUtils.getString(R.string.awaker_article_abnormal_event))
        titleArr.add(ResUtils.getString(R.string.awaker_article_conspiracy_theory))
        titleArr.add(ResUtils.getString(R.string.awaker_article_masonic))
        titleArr.add(ResUtils.getString(R.string.awaker_article_crucial_moment))
        titleArr.add(ResUtils.getString(R.string.awaker_article_other))
        return titleArr
    }

    private fun setToolbar(toolbar: Toolbar) {
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
    }

    inner class FragmentLoadTask : Runnable {

        private var position: Int = 0

        fun setPosition(pos: Int) {
            position = pos
        }

        override fun run() {
            onFragmentLoadListener?.startLoadData()
        }
    }

    override fun onDestroy() {
        handler.removeCallbacks(fragmentLoadTask)
        super.onDestroy()
    }
}

