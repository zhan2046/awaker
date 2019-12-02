package com.ruzhan.day.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awaker.imageloader.ImageLoader
import com.ruzhan.common.OnItemClickListener
import com.ruzhan.day.model.DayNewModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.day_item_image_day_new_list.*

class DayImageNewListHolder(itemView: View, listener: OnItemClickListener<DayNewModel>?) :
        RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View?
        get() = itemView

    private lateinit var dayNewModel: DayNewModel

    init {
        if (listener != null) {
            itemView.setOnClickListener {
                listener.onItemClick(it, adapterPosition, dayNewModel)
            }
        }
    }

    fun bind(bean: DayNewModel) {
        dayNewModel = bean
        ImageLoader.get().load(imagePicIv, bean.cover_landscape ?: "")
    }
}