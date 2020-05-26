package com.future.movie.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.future.movie.R
import com.future.movie.db.entity.MovieEntity
import com.future.movie.detail.fragment.MovieDetailFragment

class MovieDetailActivity : AppCompatActivity() {

    companion object {

        private const val MOVIE: String = "MOVIE"

        @JvmStatic
        fun launch(activity: Activity, movie: MovieEntity) {
            val intent = Intent(activity, MovieDetailActivity::class.java)
            intent.putExtra(MOVIE, movie)
            activity.startActivity(intent)
        }
    }

    private var movieDetailFragment: MovieDetailFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lion_container)
        val movie = intent.getParcelableExtra(MOVIE) as MovieEntity
        if (savedInstanceState == null) {
            val movieDetailFragment = MovieDetailFragment.newInstance(movie)
            this.movieDetailFragment = movieDetailFragment
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, movieDetailFragment, "MovieDetailFragment2")
                .commit()
        }
    }
}