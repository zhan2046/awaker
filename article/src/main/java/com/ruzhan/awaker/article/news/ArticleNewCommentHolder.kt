package com.ruzhan.awaker.article.news

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.awaker.imageloader.ImageLoader
import com.ruzhan.awaker.article.R
import com.ruzhan.awaker.article.model.Comment
import com.ruzhan.common.util.CommonUtils
import com.ruzhan.common.util.ResUtils
import com.ruzhan.lion.helper.FontHelper
import kotlinx.android.synthetic.main.awaker_article_item_new_detail_comment.view.*

class ArticleNewCommentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var comment: Comment

    init {
        CommonUtils.setTint(itemView.context, R.mipmap.awaker_article_zan, R.color.awaker_article_grey_zan,
                itemView.zan_iv)

        itemView.zan_ll.setOnClickListener {
            comment.isSelect = !comment.isSelect
            val color = if (comment.isSelect) R.color.awaker_article_blue_zan else R.color.awaker_article_grey_zan
            CommonUtils.setTint(itemView.context, R.mipmap.awaker_article_zan, color, itemView.zan_iv)
            val up = Integer.valueOf(comment.up)
            val newUp = if (comment.isSelect) up + 1 else up
            itemView.zan_tv.text = newUp.toString()
        }

        itemView.name_tv.typeface = FontHelper.get().getBoldTypeface()
        itemView.area_tv.typeface = FontHelper.get().getLightTypeface()
        itemView.content_tv.typeface = FontHelper.get().getLightTypeface()
        itemView.zan_tv.typeface = FontHelper.get().getLightTypeface()
        itemView.time_tv.typeface = FontHelper.get().getLightTypeface()
    }

    fun bind(bean: Comment) {
        comment = bean

        itemView.content_tv.text = bean.content
        itemView.time_tv.text = bean.create_time
        itemView.area_tv.text = "( " + bean.area + " )"
        itemView.zan_tv.text = bean.up
        isSelect(comment.isSelect)

        val user = bean.user
        if (user != null) {
            val userName = if (TextUtils.isEmpty(user.real_nickname))
                ResUtils.getString(R.string.awaker_article_up_comment_guest)
            else
                user.real_nickname
            itemView.name_tv.text = userName

            val isGuest = "0" == bean.uid
            itemView.name_tv.setTextColor(if (isGuest)
                Color.parseColor("#FF383838")
            else
                Color.parseColor("#FFEC6A5C"))
            if (isGuest) {
                itemView.icon_iv.setImageResource(R.drawable.awaker_article_ic_gongjihui)

            } else {
                ImageLoader.get().loadCropCircle(itemView.icon_iv, user.avatar64)
            }
        }

        if (!TextUtils.isEmpty(bean.sina_name) && !TextUtils.isEmpty(bean.sina_avatar)) {
            itemView.name_tv.text = bean.sina_name
            itemView.name_tv.setTextColor(Color.parseColor("#FF60C5BA"))
            ImageLoader.get().loadCropCircle(itemView.icon_iv, bean.sina_avatar)
        }
    }

    private fun isSelect(isSelect: Boolean) {
        val color = if (isSelect) R.color.awaker_article_blue_zan else R.color.awaker_article_grey_zan
        CommonUtils.setTint(itemView.context, R.mipmap.awaker_article_zan, color, itemView.zan_iv)
        val up = Integer.valueOf(comment.up)
        val newUp = if (isSelect) up + 1 else up
        itemView.zan_tv.text = newUp.toString()
    }
}