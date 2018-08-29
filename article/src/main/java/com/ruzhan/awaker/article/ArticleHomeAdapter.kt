package com.ruzhan.awaker.article

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.ruzhan.awaker.article.news.ArticleNewAllFragment
import com.ruzhan.awaker.article.news.OtherArticleNewAllFragment
import com.ruzhan.awaker.article.week.ArticleWeekHotCommentFragment
import com.ruzhan.awaker.article.week.ArticleWeekHotReadFragment
import java.lang.ref.WeakReference

/**
 * Created by ruzhan123 on 2018/8/28.
 */
class ArticleHomeAdapter(fm: FragmentManager, private val titleList: List<String>)
    : FragmentStatePagerAdapter(fm) {

    private val fragMap: HashMap<Int, WeakReference<OnFragmentLoadListener>> = HashMap()

    override fun getItem(position: Int): Fragment {
        lateinit var frag: Fragment
        when (position) {
            0 -> frag = ArticleNewAllFragment.newInstance() // 全部资讯
            1 -> frag = ArticleWeekHotReadFragment.newInstance() // 每周热读
            2 -> frag = ArticleWeekHotCommentFragment.newInstance() // 每周热评
            3 -> frag = OtherArticleNewAllFragment.newInstance(409) // 灵性觉醒
            4 -> frag = OtherArticleNewAllFragment.newInstance(6) // 科学探索
            5 -> frag = OtherArticleNewAllFragment.newInstance(1) // UFO
            6 -> frag = OtherArticleNewAllFragment.newInstance(27) // 自由能源
            7 -> frag = OtherArticleNewAllFragment.newInstance(408) // 未解之谜
            8 -> frag = OtherArticleNewAllFragment.newInstance(4) // 麦田圈
            9 -> frag = OtherArticleNewAllFragment.newInstance(153) // 反转基因
            10 -> frag = OtherArticleNewAllFragment.newInstance(13) // 异常事件
            11 -> frag = OtherArticleNewAllFragment.newInstance(5) // 阴谋论
            12 -> frag = OtherArticleNewAllFragment.newInstance(320) // 共济会
            13 -> frag = OtherArticleNewAllFragment.newInstance(10) // 关键时刻
            14 -> frag = OtherArticleNewAllFragment.newInstance(7) // 其他
        }
        if (frag is OnFragmentLoadListener) {
            fragMap[position] = WeakReference<OnFragmentLoadListener>(frag)
        }
        return frag
    }

    override fun getCount(): Int {
        return titleList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    fun getOnFragmentLoadListener(position: Int): OnFragmentLoadListener? {
        var listener: OnFragmentLoadListener? = null
        val weakRefValue = fragMap[position]
        weakRefValue?.let {
            val realValue = it.get()
            realValue?.let { listener = it }
        }
        return listener
    }
}