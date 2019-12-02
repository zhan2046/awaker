package com.ruzhan.day.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awaker.imageloader.ImageLoader
import com.lion.font.FontHelper
import com.awaker.common.OnItemClickListener
import com.ruzhan.day.model.DayNewModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.day_item_day_new.*

class DayNewHolder(itemView: View, listener: OnItemClickListener<DayNewModel>?) :
        RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View?
        get() = itemView

    private lateinit var dayNewModel: DayNewModel

    init {
        titleTv.typeface = FontHelper.get().boldFontTypeface
        tagTv.typeface = FontHelper.get().lightFontTypeface
        contentTv.typeface = FontHelper.get().lightFontTypeface

        if (listener != null) {
            itemView.setOnClickListener { view ->
                listener.onItemClick(view, adapterPosition, dayNewModel)
            }
        }
    }

    fun bind(bean: DayNewModel) {
        dayNewModel = bean
        ImageLoader.get().load(picIv, bean.cover_landscape ?: "")
        titleTv.text = bean.title
        handleContentText(bean)
    }

    private fun handleContentText(bean: DayNewModel) {
        val tags = bean.tags
        var tagStr = ""
        if (tags != null && tags.isNotEmpty()) {
            tagStr = tags[0].name ?: ""
        }
        tagTv.text = tagStr
        contentTv.text = bean.content
    }
}