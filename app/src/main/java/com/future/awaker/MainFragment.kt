package com.future.awaker

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ruzhan.awaker.article.news.ArticleMovieListFragment
import com.ruzhan.day.DayNewFragment
import kotlinx.android.synthetic.main.frag_main.*

class MainFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }

    private val fragmentMap = HashMap<String, Fragment>()

    private var dayNewFragment: DayNewFragment? = null
    private var articleMovieListFragment: ArticleMovieListFragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottom_navigation.setTextVisibility(false)
        bottom_navigation.enableAnimation(false)

        bottom_navigation.setOnNavigationItemSelectedListener {
            if (bottom_navigation.selectedItemId != it.itemId) {
                replaceFragment(it.itemId)
            }
            true
        }

        replaceFragment(R.id.article)
    }

    private fun replaceFragment(tabId: Int) {
        val fm = childFragmentManager
        val transaction = fm.beginTransaction()

        for (frag in fragmentMap.values) {
            transaction.hide(frag)
        }

        var fragTag: String? = null
        var frag: Fragment? = null

        when (tabId) {
            R.id.article -> {
                fragTag = "DayNewFragment"
                frag = fragmentMap[fragTag]

                if (frag == null) {
                    frag = DayNewFragment.newInstance()
                    dayNewFragment = frag
                    transaction.add(R.id.container, frag, fragTag)

                } else {
                    transaction.show(frag)
                }

            }
            R.id.movie -> {
                fragTag = "ArticleMovieListFragment"
                frag = fragmentMap[fragTag]

                if (frag == null) {
                    frag = ArticleMovieListFragment.newInstance()
                    articleMovieListFragment = frag
                    transaction.add(R.id.container, frag, fragTag)

                } else {
                    transaction.show(frag)
                }
            }
        }
        if (fragTag != null && frag != null) {
            fragmentMap[fragTag] = frag
        }

        if (!fm.isDestroyed) {
            transaction.commitAllowingStateLoss()
        }
    }
}