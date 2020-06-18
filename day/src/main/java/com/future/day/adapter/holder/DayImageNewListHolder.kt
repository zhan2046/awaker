package com.future.day.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.future.day.base.OnItemClickListener
import com.future.day.db.entity.DayNew
import com.future.day.db.entity.DayNewChild
import com.future.imageloader.glide.ImageLoader
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.day_item_image_day_new_list.*

class DayImageNewListHolder(itemView: View, listener: OnItemClickListener<Any>?) :
    RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View?
        get() = itemView

    private lateinit var dayNewModel: Any

    init {
        if (listener != null) {
            imagePicIv.setOnClickListener {
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