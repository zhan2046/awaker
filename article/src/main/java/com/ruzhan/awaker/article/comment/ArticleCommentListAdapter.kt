package com.ruzhan.awaker.article.comment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ruzhan.awaker.article.R
import com.ruzhan.awaker.article.model.Comment
import com.ruzhan.awaker.article.news.ArticleNewCommentHolder
import com.ruzhan.common.util.ConstantUtils
import java.util.*

class ArticleCommentListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_MORE = 1000
        private const val TYPE_ITEM = 1001

        private const val LOAD_MORE = "loadMore"
    }

    private val dataList = ArrayList<Any>()
    private var isLoadMore: Boolean = false


    fun setRefreshData(list: List<Comment>) {
        if (list.isNotEmpty()) {
            dataList.clear()
            dataList.addAll(list)
            dataList.add(LOAD_MORE)
            isLoadMore = list.size >= ConstantUtils.PAGE_SIZE
            notifyDataSetChanged()
        }
    }

    fun setLoadMoreData(list: List<Comment>) {
        if (list.isNotEmpty()) {
            dataList.remove(LOAD_MORE)

            dataList.addAll(list)
            dataList.add(LOAD_MORE)
            isLoadMore = list.size >= ConstantUtils.PAGE_SIZE
            notifyDataSetChanged()
        }
    }

    fun setLoadMore(isLoad: Boolean) {
        isLoadMore = isLoad
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val obj = dataList[position]
        return if (obj is String) {
            TYPE_MORE
        } else TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        lateinit var viewHolder: RecyclerView.ViewHolder
        when (viewType) {
            TYPE_ITEM -> viewHolder = ArticleNewCommentHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.awaker_article_item_new_detail_comment, parent, false))

            TYPE_MORE -> viewHolder = ArticleCommentLoadMoreHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.awaker_article_item_load, parent, false))
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_ITEM -> (holder as ArticleNewCommentHolder).bind(dataList[position] as Comment)
            TYPE_MORE -> (holder as ArticleCommentLoadMoreHolder).bind(isLoadMore)
        }
    }
}