package com.ruzhan.day.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.awaker.common.OnItemClickListener
import com.awaker.imageloader.ImageLoader
import com.ruzhan.day.db.entity.DayNew
import com.ruzhan.day.db.entity.DayNewChild
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.day_item_image_day_new_list.*

class DayImageNewListHolder(itemView: View, listener: OnItemClickListener<Any>?) :
        RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View?
        get() = itemView

    private lateinit var dayNewModel: Any

    init {
        if (listener != null) {
            itemView.setOnClickListener {
                listener.onItemClick(it, adapterPosition, dayNewModel)
            }
        }
    }

    fun bind(bean: Any) {
        dayNewModel = bean
        if (bean is DayNew) {
            ImageLoader.get().load(imagePicIv, bean.cover_landscape)
        } else if (bean is DayNewChild) {
            ImageLoader.get().load(imagePicIv, bean.cover_landscape)
        }
    }
}