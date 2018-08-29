package com.ruzhan.awaker.article.comment

import android.support.v7.widget.RecyclerView
import android.view.View
import com.ruzhan.awaker.article.R
import com.ruzhan.lion.helper.FontHelper
import kotlinx.android.synthetic.main.awaker_article_item_load.view.*

/**
 * Created by ruzhan123 on 2018/8/29.
 */
class ArticleCommentLoadMoreHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(isLoadMore: Boolean) {
        itemView.more_tv.typeface = FontHelper.get().getLightTypeface()

        itemView.load_progress.visibility = if (isLoadMore) View.VISIBLE else View.GONE
        itemView.more_tv.text = if (isLoadMore)
            itemView.resources.getString(R.string.awaker_article_loading_desc)
        else
            itemView.resources.getString(R.string.awaker_article_loaded_desc)
    }
}