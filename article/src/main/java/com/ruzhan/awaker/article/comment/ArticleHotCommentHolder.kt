package com.ruzhan.awaker.article.comment

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.ruzhan.awaker.article.R
import com.ruzhan.awaker.article.model.Comment
import com.ruzhan.awaker.article.util.ResUtils
import com.ruzhan.awaker.article.util.UiUtils
import com.ruzhan.common.imageloader.ImageLoader
import com.ruzhan.lion.helper.FontHelper
import com.ruzhan.lion.listener.OnItemClickListener
import kotlinx.android.synthetic.main.awaker_article_item_new_hot_comment.view.*

class ArticleHotCommentHolder(itemView: View, private val listener: OnItemClickListener<Comment>)
    : RecyclerView.ViewHolder(itemView) {

    private lateinit var comment: Comment

    init {
        UiUtils.setTint(itemView.context, R.mipmap.awaker_article_zan, R.color.awaker_article_grey_zan,
                itemView.zan_iv)

        itemView.root_card.setOnClickListener { listener.onItemClick(adapterPosition, comment, it) }

        itemView.zan_ll.setOnClickListener {
            comment.isSelect = !comment.isSelect
            val color = if (comment.isSelect) R.color.awaker_article_blue_zan else R.color.awaker_article_grey_zan
            UiUtils.setTint(itemView.context, R.mipmap.awaker_article_zan, color, itemView.zan_iv)
            val up = Integer.valueOf(comment.up)
            val newUp = if (comment.isSelect) up + 1 else up
            itemView.zan_tv.text = newUp.toString()
        }

        itemView.name_tv.typeface = FontHelper.get().getBoldTypeface()
        itemView.area_tv.typeface = FontHelper.get().getLightTypeface()
        itemView.content_tv.typeface = FontHelper.get().getLightTypeface()
        itemView.zan_tv.typeface = FontHelper.get().getLightTypeface()
        itemView.new_title_tv.typeface = FontHelper.get().getLightTypeface()
    }


    @SuppressLint("SetTextI18n")
    fun bind(bean: Comment) {
        comment = bean

        itemView.content_tv.text = bean.content
        val newTitle = if (bean.newstitle == null) "" else bean.newstitle.title
        itemView.new_title_tv.text = "@$newTitle"
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
        UiUtils.setTint(itemView.context, R.mipmap.awaker_article_zan, color, itemView.zan_iv)
        val up = Integer.valueOf(comment.up)
        val newUp = if (isSelect) up + 1 else up
        itemView.zan_tv.text = newUp.toString()
    }
}