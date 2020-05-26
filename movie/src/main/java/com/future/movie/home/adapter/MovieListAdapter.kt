package com.future.movie.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.future.movie.R
import com.future.movie.base.LoadMoreHolder
import com.future.movie.db.entity.MovieEntity
import com.future.movie.home.adapter.holder.MovieEmptyHolder
import com.future.movie.home.adapter.holder.MovieListHolder
import com.future.movie.listener.OnItemClickListener


class MovieListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val LOAD_MORE: String = "LOAD_MORE"

        const val TYPE_NORMAL: Int = 1000
        const val TYPE_LOAD_MORE: Int = 1001
    }

    private val dataList = ArrayList<Any>()
    private var isLoadMore: Boolean = false

    var onItemClickListener: OnItemClickListener<MovieEntity>? = null

    fun setData(movieList: List<MovieEntity>) {
        dataList.clear()
        dataList.addAll(movieList)
        dataList.add(LOAD_MORE)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataList[position]) {
            is String -> TYPE_LOAD_MORE
            is MovieEntity -> TYPE_NORMAL
            else -> TYPE_NORMAL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_NORMAL -> MovieListHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.lion_item_movie_list, parent, false),
                onItemClickListener)

            TYPE_LOAD_MORE -> LoadMoreHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.lion_item_load_more, parent, false))

            else -> MovieEmptyHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.lion_item_empty, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_NORMAL -> {
                val viewHolder = holder as MovieListHolder
                val bean = dataList[position] as MovieEntity
                viewHolder.bind(bean)
            }
            TYPE_LOAD_MORE -> {
                val viewHolder = holder as LoadMoreHolder
                viewHolder.bind(isLoadMore)
            }
        }
    }

}