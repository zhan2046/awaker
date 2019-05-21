package com.ruzhan.awaker.article.week

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ruzhan.awaker.article.R
import com.ruzhan.awaker.article.model.News
import com.ruzhan.awaker.article.news.ArticleNewAllHolder
import com.ruzhan.lion.listener.OnItemClickListener
import java.util.*

class ArticleWeekHotReadAdapter(private val listener: OnItemClickListener<News>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList = ArrayList<Any>()

    fun setRefreshData(list: List<News>) {
        if (list.isNotEmpty()) {
            dataList.clear()
            dataList.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArticleNewAllHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.awaker_article_item_new_list_grid, parent, false), listener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ArticleNewAllHolder).bind(dataList[position] as News)
    }
}