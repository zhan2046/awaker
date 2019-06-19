package com.ruzhan.awaker.article.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ruzhan.awaker.article.R
import com.ruzhan.common.TitleHelper
import com.ruzhan.lion.helper.FontHelper
import com.ruzhan.movie.MovieListFragment
import kotlinx.android.synthetic.main.awaker_article_movie.*

class ArticleMovieListFragment : Fragment() {

    companion object {

        @JvmStatic
        fun newInstance() = ArticleMovieListFragment()
    }

    private var movieListFragment: MovieListFragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.awaker_article_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title_tv.typeface = FontHelper.get().getBoldTypeface()
        title_tv.text = resources.getString(R.string.awaker_article_movie_title)
        TitleHelper.setToolbar(toolbar, activity)

        if (movieListFragment == null) {
            movieListFragment = MovieListFragment.newInstance()
            childFragmentManager
                    .beginTransaction()
                    .add(R.id.container, movieListFragment, "MovieListFragment")
                    .commit()
        }
        TitleHelper.setAlphaScaleAnimate(title_tv)
    }
}