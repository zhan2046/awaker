package com.future.movie.detail.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.future.media.MediaControllerManager
import com.google.android.material.appbar.AppBarLayout
import com.ruzhan.imageloader.glide.ImageLoader
import com.future.movie.R
import com.future.movie.db.entity.MovieEntity
import com.future.movie.db.entity.VideoItem
import com.future.movie.decoration.VideoItemDecoration
import com.future.movie.detail.activity.ImageDetailActivity
import com.future.movie.detail.adapter.MovieDetailAdapter
import com.future.movie.detail.viewmodel.MovieDetailViewModel
import com.future.movie.listener.AppBarStateChangeListener
import com.future.movie.listener.OnItemClickListener
import com.future.movie.model.ImageListModel
import com.future.movie.utils.ViewUtils
import com.future.movie.video.VideoActivity
import kotlinx.android.synthetic.main.lion_frag_movie_detail.*


class MovieDetailFragment : Fragment() {

    companion object {

        private const val MOVIE: String = "MOVIE"
        private const val HEADER_OFFSET = 9.0f / 16.0f

        @JvmStatic
        fun newInstance(movie: MovieEntity): MovieDetailFragment {
            val args = Bundle()
            args.putParcelable(MOVIE, movie)
            val frag = MovieDetailFragment()
            frag.arguments = args
            return frag
        }
    }

    private lateinit var movie: MovieEntity
    private val movieDetailAdapter = MovieDetailAdapter()
    private val movieDetailViewModel: MovieDetailViewModel by lazy {
        ViewModelProviders.of(this).get(MovieDetailViewModel::class.java)
    }
    private val videoItemDecoration: VideoItemDecoration by lazy {
        VideoItemDecoration(requireActivity())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = arguments?.getParcelable(MOVIE) as MovieEntity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.lion_frag_movie_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        MediaControllerManager.get() // init exoplayer
        initData()
        initListener()
        movieDetailViewModel.getMovieDetail(movie.id)
    }

    private fun initData() {
        val activity = requireActivity() as AppCompatActivity
        collapsingToolbarLayout.isTitleEnabled = false
        activity.setSupportActionBar(toolbar)
        val supportActionBar = activity.supportActionBar
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true)
            supportActionBar.setHomeButtonEnabled(true)
        }
        ImageLoader.get().loadNoCrossFade(headIv, movie.image,
            ViewUtils.getPlaceholder(activity, 0))

        val layoutManager = GridLayoutManager(activity, MovieDetailAdapter.SPAN_COUNT,
            GridLayoutManager.VERTICAL, false)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return movieDetailAdapter.getSpanSize(position)
            }
        }
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = movieDetailAdapter
        recyclerView.addItemDecoration(videoItemDecoration)
        movieDetailAdapter.videoItemDecoration = videoItemDecoration

        val layoutParams = appBarLayout.layoutParams
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        layoutParams.height = (width * HEADER_OFFSET).toInt()
    }

    private fun initListener() {
        val activity = requireActivity()
        toolbar.setNavigationOnClickListener {
            activity.finish()
        }
        movieDetailAdapter.onItemVideoClickListener = object : OnItemClickListener<VideoItem> {

            override fun onItemClick(position: Int, bean: VideoItem, itemView: View) {
                VideoActivity.launch(activity, bean.m3u8Url)
            }
        }
        movieDetailAdapter.onItemImageClickListener =
            object : OnItemClickListener<ImageListModel> {

                override fun onItemClick(position: Int, bean: ImageListModel, itemView: View) {
                    ImageDetailActivity.launch(activity, bean)
                }
            }
        movieDetailViewModel.movieDetailLiveData.observe(viewLifecycleOwner,
            Observer { movieDetail ->
                movieDetail?.let {
                    movieDetailAdapter.setData(it)
                }
            })
        appBarLayout.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                when (state) {
                    State.EXPANDED -> {
                        toolbar.title = ""
                    }
                    State.COLLAPSED -> {
                        toolbar.title = movie.title
                    }
                    State.IDLE -> {
                        toolbar.title = ""
                    }
                    else -> {
                        toolbar.title = ""
                    }
                }
            }
        })
    }
}