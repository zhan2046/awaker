package com.future.awaker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.future.day.DayHomeFragment
import com.ruzhan.movie.home.MovieHomeFragment
import kotlinx.android.synthetic.main.frag_main.*

class MainFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
    }

    private val fragmentMap = HashMap<String, Fragment>()

    private var dayHomeFragment: DayHomeFragment? = null
    private var movieHomeFragment: MovieHomeFragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            if (bottomNavigationView.selectedItemId != it.itemId) {
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
                fragTag = "DayHomeFragment"
                frag = fragmentMap[fragTag]

                if (frag == null) {
                    frag = DayHomeFragment.newInstance()
                    dayHomeFragment = frag
                    transaction.add(R.id.container, frag, fragTag)

                } else {
                    transaction.show(frag)
                }

            }
            R.id.movie -> {
                fragTag = "MovieHomeFragment"
                frag = fragmentMap[fragTag]

                if (frag == null) {
                    frag = MovieHomeFragment.newInstance()
                    movieHomeFragment = frag
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