package com.ruzhan.day.adapter.holder

import android.view.View
import com.awaker.imageloader.ImageLoader
import com.lion.font.FontHelper
import com.ruzhan.common.OnItemClickListener
import com.ruzhan.day.model.DayNewModel
import kotlinx.android.synthetic.main.day_item_day_new.view.*

class DayNewHolder(itemView: View, listener: OnItemClickListener<DayNewModel>?) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    private lateinit var dayNewModel: DayNewModel

    init {
        itemView.titleTv.typeface = FontHelper.get().getBoldTypeface()
        itemView.tagTv.typeface = FontHelper.get().getLightTypeface()
        itemView.contentTv.typeface = FontHelper.get().getLightTypeface()

        if (listener != null) {
            itemView.setOnClickListener { view ->
                listener.onItemClick(view, adapterPosition, dayNewModel)
            }
        }
    }

    fun bind(bean: DayNewModel) {
        dayNewModel = bean
        ImageLoader.get().load(itemView.picIv, bean.cover_landscape ?: "")
        val tags = bean.tags
        var tagStr = ""
        if (tags != null && tags.isNotEmpty()) {
            tagStr = tags[0].name ?: ""
        }
        itemView.titleTv.text = bean.title
        itemView.tagTv.text = tagStr
        itemView.contentTv.text = bean.content
    }
}