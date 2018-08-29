package com.ruzhan.awaker.article.news

import android.support.v7.widget.RecyclerView
import android.view.View
import com.ruzhan.awaker.article.imageloader.ImageLoader
import com.ruzhan.awaker.article.model.Header
import com.ruzhan.lion.helper.FontHelper
import kotlinx.android.synthetic.main.awaker_article_item_new_detail_header.view.*

/**
 * Created by ruzhan123 on 2018/8/29.
 */
class ArticleNewDetailHeaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.title_tv.typeface = FontHelper.get().getBoldTypeface()
        itemView.name_tv.typeface = FontHelper.get().getBoldTypeface()
        itemView.time_tv.typeface = FontHelper.get().getLightTypeface()
    }

    fun bind(bean: Header) {
        itemView.title_tv.text = bean.title
        itemView.name_tv.text = bean.userName
        itemView.time_tv.text = bean.createTime

        bean.url?.let {
            ImageLoader.get().load(itemView.icon_iv, it)
        }

        bean.userUrl?.let {
            ImageLoader.get().loadCropCircle(itemView.user_image_iv, it)
        }
    }
}