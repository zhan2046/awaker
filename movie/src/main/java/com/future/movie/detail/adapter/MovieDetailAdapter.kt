package com.future.movie.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.future.movie.R
import com.future.movie.base.LoadMoreHolder
import com.future.movie.db.entity.IntroduceItem
import com.future.movie.db.entity.MovieDetailEntity
import com.future.movie.db.entity.VideoItem
import com.future.movie.decoration.VideoItemDecoration
import com.future.movie.detail.adapter.holder.*
import com.future.movie.home.adapter.holder.MovieEmptyHolder
import com.future.movie.listener.OnItemClickListener
import com.future.movie.model.ImageListModel
import java.util.*

class MovieDetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val SPAN_COUNT = 2

        const val TYPE_TITLE: Int = 1001
        const val TYPE_TEXT: Int = 1002
        const val TYPE_IMAGE: Int = 1003
        const val TYPE_VIDEO_TITLE: Int = 1005
        const val TYPE_VIDEO: Int = 1006
        const val TYPE_LOAD_MORE: Int = 1007

        const val VIDEO_TITLE: String = "VIDEO_TITLE"
        const val LOAD_MORE: String = "LOAD_MORE"
    }

    private var dataList: ArrayList<Any> = ArrayList()
    private lateinit var movieDetail: MovieDetailEntity

    var onItemVideoClickListener: OnItemClickListener<VideoItem>? = null
    var onItemImageClickListener: OnItemClickListener<ImageListModel>? = null

    var videoItemDecoration: VideoItemDecoration? = null

    fun setData(movieDetail: MovieDetailEntity) {
        this.movieDetail = movieDetail
        dataList.clear()
        dataList.add(movieDetail.title)
        dataList.addAll(movieDetail.introduceList)
        dataList.add(VIDEO_TITLE)
        dataList.addAll(movieDetail.videoList)
        dataList.add(LOAD_MORE)
        videoItemDecoration?.setData(dataList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val obj = dataList[position]
        var viewType = 0
        if (obj is String) {
            viewType = when (obj) {
                VIDEO_TITLE -> TYPE_VIDEO_TITLE
                LOAD_MORE -> TYPE_LOAD_MORE
                else -> TYPE_TITLE
            }
        } else if (obj is IntroduceItem) {
            if (IntroduceItem.TEXT == obj.type) {
                viewType = TYPE_TEXT

            } else if (IntroduceItem.IMAGE == obj.type) {
                viewType = TYPE_IMAGE
            }
        } else if (obj is VideoItem) {
            viewType = TYPE_VIDEO
        }
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TITLE -> MovieDetailTitleHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.lion_item_movie_detail_title, parent, false))

            TYPE_TEXT -> MovieDetailTextHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.lion_item_movie_detail_text, parent, false))

            TYPE_IMAGE -> MovieDetailImageHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.lion_item_movie_detail_image, parent, false),
                movieDetail, onItemImageClickListener)

            TYPE_VIDEO -> MovieDetailVideoHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.lion_item_movie_detail_video, parent, false),
                onItemVideoClickListener)

            TYPE_VIDEO_TITLE -> MovieDetailVideoTitleHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.lion_item_movie_detail_video_title, parent, false))

            TYPE_LOAD_MORE -> LoadMoreHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.lion_item_load_more, parent, false))

            else -> MovieEmptyHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.lion_item_empty, parent, false))
        }
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_TITLE -> {
                val viewHolder = holder as MovieDetailTitleHolder
                viewHolder.bind(movieDetail.title, movieDetail.type)
            }
            TYPE_TEXT -> {
                val viewHolder = holder as MovieDetailTextHolder
                val bean = dataList[position] as IntroduceItem
                viewHolder.bind(bean)
            }
            TYPE_IMAGE -> {
                val viewHolder = holder as MovieDetailImageHolder
                val bean = dataList[position] as IntroduceItem
                viewHolder.bind(bean)
            }
            TYPE_VIDEO -> {
                val viewHolder = holder as MovieDetailVideoHolder
                val bean = dataList[position] as VideoItem
                viewHolder.bind(bean)
            }
            TYPE_LOAD_MORE -> {
                val viewHolder = holder as LoadMoreHolder
                viewHolder.bind(false)
            }
        }
    }

    fun getSpanSize(position: Int): Int {
        val obj = dataList[position]
        if (obj is VideoItem) {
            return 1
        }
        return SPAN_COUNT
    }
}