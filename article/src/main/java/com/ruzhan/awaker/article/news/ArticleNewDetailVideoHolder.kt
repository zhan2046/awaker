package com.ruzhan.awaker.article.news

import android.support.v7.widget.RecyclerView
import android.view.View
import com.ruzhan.awaker.article.imageloader.ImageLoader
import com.ruzhan.awaker.article.model.NewEle
import kotlinx.android.synthetic.main.awaker_article_item_new_detail_video.view.*

/**
 * Created by ruzhan123 on 2018/8/29.
 */
class ArticleNewDetailVideoHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    init {
        itemView.root_cv.setOnClickListener {  }
    }

    fun bind(bean: NewEle) {
        bean.imgUrl?.let {
            ImageLoader.get().load(itemView.image_iv, it)
        }
    }
}