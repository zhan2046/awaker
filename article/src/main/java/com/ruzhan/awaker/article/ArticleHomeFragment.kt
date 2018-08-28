package com.ruzhan.awaker.article

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ruzhan.awaker.article.util.ResUtils
import kotlinx.android.synthetic.main.awaker_article_frag_home.*



/**
 * Created by ruzhan123 on 2018/8/28.
 */
class ArticleHomeFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() = ArticleHomeFragment()
    }

    private lateinit var articleHomeAdapter: ArticleHomeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.awaker_article_frag_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val titleList = getTitleList()

        articleHomeAdapter = ArticleHomeAdapter(childFragmentManager, titleList)
        view_pager.adapter = articleHomeAdapter

        tabs.tabMode = android.support.design.widget.TabLayout.MODE_SCROLLABLE
        tabs.setupWithViewPager(view_pager)
    }

    private fun getTitleList() : List<String> {
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
}

