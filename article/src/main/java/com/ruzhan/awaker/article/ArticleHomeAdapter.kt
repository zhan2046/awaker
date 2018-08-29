package com.ruzhan.awaker.article

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.ruzhan.awaker.article.news.ArticleNewAllFragment
import com.ruzhan.awaker.article.week.ArticleWeekHotCommentFragment
import com.ruzhan.awaker.article.week.ArticleWeekHotReadFragment

/**
 * Created by ruzhan123 on 2018/8/28.
 */
class ArticleHomeAdapter(fm: FragmentManager, private val titleList: List<String>)
    : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        lateinit var frag: Fragment
        when (position) {
            0 -> frag = ArticleNewAllFragment.newInstance()
            1 -> frag = ArticleWeekHotReadFragment.newInstance()
            2 -> frag = ArticleWeekHotCommentFragment.newInstance()
            3 -> frag = ArticleNewAllFragment.newInstance()
            4 -> frag = ArticleNewAllFragment.newInstance()
            5 -> frag = ArticleNewAllFragment.newInstance()
            6 -> frag = ArticleNewAllFragment.newInstance()
            7 -> frag = ArticleNewAllFragment.newInstance()
            8 -> frag = ArticleNewAllFragment.newInstance()
            9 -> frag = ArticleNewAllFragment.newInstance()
            10 -> frag = ArticleNewAllFragment.newInstance()
            11 -> frag = ArticleNewAllFragment.newInstance()
            12 -> frag = ArticleNewAllFragment.newInstance()
            13 -> frag = ArticleNewAllFragment.newInstance()
            14 -> frag = ArticleNewAllFragment.newInstance()
        }
        return frag
    }

    override fun getCount(): Int {
        return titleList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }
}