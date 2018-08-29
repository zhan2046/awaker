package com.ruzhan.awaker.article.news

import android.support.v7.widget.RecyclerView
import android.view.View
import com.ruzhan.lion.helper.FontHelper
import com.ruzhan.lion.listener.OnItemClickListener
import kotlinx.android.synthetic.main.awaker_article_item_comment_more.view.*

/**
 * Created by ruzhan123 on 2018/8/29.
 */
class ArticleCommentMoreHolder(itemView: View, private val listener: OnItemClickListener<String>)
    : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.more_fl.setOnClickListener { listener.onItemClick(adapterPosition, "", it) }
        itemView.more_tv.typeface = FontHelper.get().getLightTypeface()
    }


}
