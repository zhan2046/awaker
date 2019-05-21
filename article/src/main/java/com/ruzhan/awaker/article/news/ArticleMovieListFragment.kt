package com.ruzhan.awaker.article.news

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.ruzhan.awaker.article.R
import com.ruzhan.lion.helper.FontHelper
import com.ruzhan.lion.util.AnimUtils
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
        setToolbar(toolbar)

        if (movieListFragment == null) {
            movieListFragment = MovieListFragment.newInstance()
            childFragmentManager
                    .beginTransaction()
                    .add(R.id.container, movieListFragment, "MovieListFragment")
                    .commit()
        }

        title_tv.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                title_tv.viewTreeObserver.removeOnGlobalLayoutListener(this)

                title_tv.alpha = 0f
                title_tv.scaleX = 0.8f

                title_tv.animate()
                        .alpha(1f)
                        .scaleX(1f)
                        .setDuration(900).interpolator = AnimUtils.getFastOutSlowInInterpolator(activity)
            }
        })
    }

    private fun setToolbar(toolbar: Toolbar) {
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
    }

}