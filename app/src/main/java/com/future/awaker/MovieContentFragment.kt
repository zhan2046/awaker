package com.future.awaker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lion.font.FontHelper
import com.ruzhan.common.TitleHelper
import com.ruzhan.movie.home.MovieListFragment
import kotlinx.android.synthetic.main.frag_movie_content.*

class MovieContentFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() = MovieContentFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_movie_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitle()
        if (savedInstanceState == null) {
            val movieListFragment = MovieListFragment.newInstance()
            childFragmentManager
                    .beginTransaction()
                    .add(R.id.container, movieListFragment, "MovieListFragment")
                    .commit()
        }
    }

    private fun initTitle() {
        titleTv.typeface = FontHelper.get().getBoldTypeface()
        titleTv.text = resources.getString(R.string.awaker_article_movie_title)
        TitleHelper.setToolbar(toolbar, activity)
        TitleHelper.setAlphaScaleAnimate(titleTv)
    }
}