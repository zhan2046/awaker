package com.ruzhan.awaker.article.news

import android.support.v7.widget.RecyclerView
import android.view.View
import com.ruzhan.lion.helper.FontHelper
import kotlinx.android.synthetic.main.awaker_article_item_new_detail_comment_title.view.*

/**
 * Created by ruzhan123 on 2018/8/29.
 */
class ArticleNewDetailCommentTitleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.hot_title_tv.typeface = FontHelper.get().getLightTypeface()
    }

}