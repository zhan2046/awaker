package com.future.awaker

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import com.ruzhan.awaker.article.ArticleHomeFragment
import com.ruzhan.lion.util.AnimUtils
import com.ruzhan.movie.MovieListFragment
import kotlinx.android.synthetic.main.frag_main.*

/**
 * Created by ruzhan123 on 2018/8/28.
 */
class MainFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }

    private val fragmentMap = HashMap<String, Fragment>()

    private var articleHomeFragment: ArticleHomeFragment? = null
    private var movieListFragment: MovieListFragment? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(tool_bar)

        bottom_navigation.setOnNavigationItemSelectedListener {
            if (bottom_navigation.selectedItemId != it.itemId) {
                replaceFragment(it.itemId)
            }
            true
        }

        replaceFragment(R.id.article)

        tool_bar.viewTreeObserver.addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                tool_bar.viewTreeObserver.removeOnGlobalLayoutListener(this)
                animateToolbar()
            }
        })
    }

    private fun replaceFragment(tabId: Int) {
        val fm = childFragmentManager
        val transaction = fm.beginTransaction()

        for (frag in fragmentMap.values) {
            transaction.hide(frag)
        }

        var fragTag: String? = null
        var frag: Fragment? = null

        when(tabId) {
            R.id.article -> {
                fragTag = "ArticleHomeFragment"
                frag = fragmentMap[fragTag]

                if (frag == null) {
                    frag = ArticleHomeFragment.newInstance()
                    articleHomeFragment = frag
                    transaction.add(R.id.container, frag, fragTag)

                } else {
                    transaction.show(frag)
                }

            }
            R.id.movie -> {
                fragTag = "MovieListFragment"
                frag = fragmentMap[fragTag]

                if (frag == null) {
                    frag = MovieListFragment.newInstance()
                    movieListFragment = frag
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

    private fun animateToolbar() {
        // this is gross but toolbar doesn't expose it's children to animate them :(
        val t = tool_bar.getChildAt(0)
        if (t != null && t is TextView) {
            // fade in and space out the title.  Animating the letterSpacing performs horribly so
            // fake it by setting the desired letterSpacing then animating the scaleX ¯\_(ツ)_/¯
            t.alpha = 0f
            t.scaleX = 0.8f

            t.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .setStartDelay(300)
                    .setDuration(900).interpolator = AnimUtils.getFastOutSlowInInterpolator(activity)
        }
    }
}