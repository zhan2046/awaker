package com.ruzhan.awaker.article.news

import android.support.v7.widget.RecyclerView
import android.view.View
import com.awaker.imageloader.ImageLoader
import com.ruzhan.awaker.article.R
import com.ruzhan.awaker.article.model.News
import com.ruzhan.lion.helper.FontHelper
import com.ruzhan.lion.listener.OnItemClickListener
import kotlinx.android.synthetic.main.awaker_article_item_new_list_grid.view.*

class ArticleNewAllHolder(itemView: View, private var listener: OnItemClickListener<News>)
    : RecyclerView.ViewHolder(itemView) {

    private lateinit var news: News

    init {
        itemView.title_tv.typeface = FontHelper.get().getLightTypeface()
        itemView.category_tv.typeface = FontHelper.get().getLightTypeface()
        itemView.comment_tv.typeface = FontHelper.get().getLightTypeface()

        itemView.root_cv.setOnClickListener { listener.onItemClick(adapterPosition, news, itemView) }
    }

    fun bind(bean: News) {
        news = bean

        itemView.title_tv.text = news.title
        itemView.category_tv.text = news.category_title

        val commentStr = String.format(itemView.resources
                .getString(R.string.awaker_article_comment_count), bean.comment)
        itemView.comment_tv.text = commentStr

        news.cover_url?.let {
            ImageLoader.get().load(itemView.icon_iv, it.ori!!)
        }
    }
}