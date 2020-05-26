package com.future.movie.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.future.movie.R
import com.future.movie.db.entity.MovieEntity
import com.future.movie.detail.MovieDetailActivity
import com.future.movie.helper.OnRefreshHelper
import com.future.movie.home.adapter.MovieListAdapter
import com.future.movie.home.viewmodel.MovieHomeViewModel
import com.future.movie.home.viewmodel.MovieListViewModel
import com.future.movie.listener.OnItemClickListener
import kotlinx.android.synthetic.main.lion_frag_movie_list.*

class MovieListFragment : Fragment() {

    companion object {

        private const val TAG_KEY = "TAG_KEY"

        @JvmStatic
        fun newInstance(): MovieListFragment {
            val args = Bundle()
            val frag = MovieListFragment()
            frag.arguments = args
            return frag
        }

        @JvmStatic
        fun newInstance(tagKey: String): MovieListFragment {
            val args = Bundle()
            args.putString(TAG_KEY, tagKey)
            val frag = MovieListFragment()
            frag.arguments = args
            return frag
        }
    }

    private val movieHomeViewModel: MovieHomeViewModel by lazy {
        ViewModelProviders.of(requireActivity()).get(MovieHomeViewModel::class.java)
    }
    private val movieListViewModel: MovieListViewModel by lazy {
        ViewModelProviders.of(this).get(MovieListViewModel::class.java)
    }
    private val movieListAdapter = MovieListAdapter()
    private var tagKey = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tagKey = arguments?.getString(TAG_KEY) ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.lion_frag_movie_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        initListener()
        initLiveData()
        movieListViewModel.loadMovieEntityList(tagKey)
    }

    private fun initData() {
        recycler_view.adapter = movieListAdapter
    }

    private fun initListener() {
        OnRefreshHelper.setOnRefreshStatusListener(swipe_refresh, recycler_view, object :
            OnRefreshHelper.OnRefreshStatusListener {

            override fun onRefresh() {
                movieHomeViewModel.getMovieList()
            }

            override fun onLoadMore() {
                // do nothing
            }
        }, R.color.colorAccent)
        movieListAdapter.onItemClickListener = object : OnItemClickListener<MovieEntity> {
            override fun onItemClick(position: Int, bean: MovieEntity, itemView: View) {
                MovieDetailActivity.launch(requireActivity(), bean)
            }
        }
    }

    private fun initLiveData() {
        movieHomeViewModel.loadStatusLiveData.observe(viewLifecycleOwner,
            Observer { isLoading ->
                if (isLoading != null && !isLoading) {
                    swipe_refresh.isRefreshing = isLoading
                }
            })
        movieListViewModel.movieListLiveData.observe(viewLifecycleOwner,
            Observer { movieList ->
                movieListAdapter.setData(movieList)
            })
    }
}