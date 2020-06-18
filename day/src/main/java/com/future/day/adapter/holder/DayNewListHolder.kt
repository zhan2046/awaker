package com.future.day.adapter.holder

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.future.day.adapter.DayImageNewListAdapter
import com.future.day.base.OnItemClickListener
import com.future.day.db.entity.DayNew
import com.future.day.db.entity.DayNewChild
import com.future.font.FontHelper
import com.future.imageloader.glide.ImageLoader
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.day_item_day_new_list.*

class DayNewListHolder(itemView: View, listener: OnItemClickListener<DayNew>?) :
    RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View?
        get() = itemView

    private lateinit var dayNewModel: DayNew
    private val dayImageNewListAdapter = DayImageNewListAdapter()
    private val imageDayNewModelList = ArrayList<Any>()

    init {
        titleTv.typeface = FontHelper.get().boldFontTypeface
        tagTv.typeface = FontHelper.get().lightFontTypeface
        contentTv.typeface = FontHelper.get().lightFontTypeface

        if (listener != null) {
            picIv.setOnClickListener { view ->
                listener.onItemClick(view, adapterPosition, dayNewModel)
            }
        }
        val layoutManager = LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL,
            false)
        newListRecyclerView.layoutManager = layoutManager
        newListRecyclerView.adapter = dayImageNewListAdapter
        dayImageNewListAdapter.onItemClickListener = object : OnItemClickListener<Any> {
            override fun onItemClick(itemView: View, position: Int, bean: Any) {
                if (bean is DayNew) {
                    dayNewModel.cover_thumb = bean.cover_landscape_hd
                    ImageLoader.get().load(picIv, bean.cover_landscape)
                    contentTv.text = bean.content
                    dayImageNewListAdapter.notifyDataSetChanged()
                } else if (bean is DayNewChild) {
                    dayNewModel.cover_thumb = bean.cover_landscape_hd
                    ImageLoader.get().load(picIv, bean.cover_landscape)
                    contentTv.text = bean.content
                    dayImageNewListAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun bind(bean: DayNew) {
        dayNewModel = bean
        dayNewModel.cover_thumb = bean.cover_landscape_hd
        ImageLoader.get().load(picIv, bean.cover_landscape)
        titleTv.text = bean.title
        handleContentText(bean)
        handleImageNewList(bean)
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

    private fun handleImageNewList(bean: DayNew) {
        imageDayNewModelList.clear()
        imageDayNewModelList.add(bean)
        val album = bean.album
        if (album.isNotEmpty()) {
            imageDayNewModelList.addAll(album)
        }
        dayImageNewListAdapter.setData(imageDayNewModelList)
    }
}
