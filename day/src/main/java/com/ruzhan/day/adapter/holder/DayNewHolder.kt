package com.ruzhan.day.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awaker.common.OnItemClickListener
import com.awaker.imageloader.ImageLoader
import com.lion.font.FontHelper
import com.ruzhan.day.db.entity.DayNew
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.day_item_day_new.*

class DayNewHolder(itemView: View, listener: OnItemClickListener<DayNew>?) :
        RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View?
        get() = itemView

    private lateinit var dayNew: DayNew

    init {
        titleTv.typeface = FontHelper.get().boldFontTypeface
        tagTv.typeface = FontHelper.get().lightFontTypeface
        contentTv.typeface = FontHelper.get().lightFontTypeface

        if (listener != null) {
            itemView.setOnClickListener { view ->
                listener.onItemClick(view, adapterPosition, dayNew)
            }
        }
    }

    fun bind(bean: DayNew) {
        dayNew = bean
        ImageLoader.get().load(picIv, bean.cover_landscape)
        titleTv.text = bean.title
        handleContentText(bean)
    }

    private fun handleContentText(bean: DayNew) {
        val tags = bean.tags
        var tagStr = ""
        if (tags.isNotEmpty()) {
            tagStr = tags[0].name
        }
        tagTv.text = tagStr
        contentTv.text = bean.content
    }
}