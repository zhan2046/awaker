package com.future.awaker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ruzhan.movie.MovieHomeFragment

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
        if (savedInstanceState == null) {
            val movieListFragment = MovieHomeFragment.newInstance()
            childFragmentManager
                    .beginTransaction()
                    .add(R.id.container, movieListFragment, "MovieHomeFragment")
                    .commit()
        }
    }
}