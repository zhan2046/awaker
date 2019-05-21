package com.ruzhan.awaker.article.comment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ruzhan.awaker.article.R
import com.ruzhan.awaker.article.model.Comment
import com.ruzhan.lion.listener.OnItemClickListener
import java.util.*

class ArticleHotCommentAdapter(private val listener: OnItemClickListener<Comment>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList = ArrayList<Any>()

    fun setRefreshData(list: List<Comment>) {
        if (list.isNotEmpty()) {
            dataList.clear()
            dataList.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArticleHotCommentHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.awaker_article_item_new_hot_comment, parent, false), listener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ArticleHotCommentHolder).bind(dataList[position] as Comment)
    }
}