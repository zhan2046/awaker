package com.ruzhan.awaker.article.news

import android.support.v7.widget.RecyclerView
import android.view.View
import com.ruzhan.awaker.article.model.NewEle
import com.ruzhan.lion.helper.FontHelper
import kotlinx.android.synthetic.main.awaker_article_item_new_detail_text.view.*

/**
 * Created by ruzhan123 on 2018/8/29.
 */
class ArticleNewDetailTextHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var newEle: NewEle

    init {
        itemView.text_tv.typeface = FontHelper.get().getLightTypeface()
    }

    fun bind(bean: NewEle) {
        newEle = bean
        itemView.text_tv.text = bean.text
    }

}