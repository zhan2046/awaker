package com.ruzhan.awaker.article.news

import android.support.v7.widget.RecyclerView
import android.view.View
import com.ruzhan.awaker.article.imageloader.ImageLoader
import com.ruzhan.awaker.article.model.NewEle
import com.ruzhan.lion.helper.FontHelper
import com.ruzhan.lion.listener.OnItemClickListener
import kotlinx.android.synthetic.main.awaker_article_item_new_detail_video.view.*

/**
 * Created by ruzhan123 on 2018/8/29.
 */
class ArticleNewDetailVideoHolder(itemView: View, private val listener: OnItemClickListener<NewEle>)
    : RecyclerView.ViewHolder(itemView) {

    private lateinit var newEle: NewEle

    init {
        itemView.video_desc_tv.typeface = FontHelper.get().getLightTypeface()

        itemView.root_cv.setOnClickListener { listener.onItemClick(adapterPosition, newEle, it) }
    }

    fun bind(bean: NewEle) {
        newEle = bean
        bean.imgUrl?.let {
            ImageLoader.get().load(itemView.image_iv, it)
        }
    }
}