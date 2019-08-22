package com.ruzhan.day.adapter.holder

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.awaker.imageloader.ImageLoader
import com.lion.font.FontHelper
import com.ruzhan.common.OnItemClickListener
import com.ruzhan.day.adapter.DayImageNewListAdapter
import com.ruzhan.day.model.DayNewModel
import kotlinx.android.synthetic.main.day_item_day_new_list.view.*

class DayNewListHolder(itemView: View, listener: OnItemClickListener<DayNewModel>?) :
        RecyclerView.ViewHolder(itemView) {

    private lateinit var dayNewModel: DayNewModel
    private val dayImageNewListAdapter = DayImageNewListAdapter()
    private val imageDayNewModelList = ArrayList<DayNewModel>()

    init {
        itemView.titleTv.typeface = FontHelper.get().getBoldTypeface()
        itemView.tagTv.typeface = FontHelper.get().getLightTypeface()
        itemView.contentTv.typeface = FontHelper.get().getLightTypeface()

        if (listener != null) {
            itemView.setOnClickListener { view ->
                listener.onItemClick(view, adapterPosition, dayNewModel)
            }
        }
        val layoutManager = LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL,
                false)
        itemView.newListRecyclerView.layoutManager = layoutManager
        itemView.newListRecyclerView.adapter = dayImageNewListAdapter
        dayImageNewListAdapter.onItemClickListener = object : OnItemClickListener<DayNewModel> {
            override fun onItemClick(itemView: View, position: Int, bean: DayNewModel) {
                val tagItemView = this@DayNewListHolder.itemView
                ImageLoader.get().load(tagItemView.picIv, bean.cover_landscape ?: "")
                tagItemView.contentTv.text = bean.content
                dayImageNewListAdapter.notifyDataSetChanged()
            }
        }
    }

    fun bind(bean: DayNewModel) {
        dayNewModel = bean
        ImageLoader.get().load(itemView.picIv, bean.cover_landscape ?: "")
        itemView.titleTv.text = bean.title
        handleContentText(bean)
        handleImageNewList(bean)
    }

    private fun handleContentText(bean: DayNewModel) {
        val tags = bean.tags
        var tagStr = ""
        if (tags != null && tags.isNotEmpty()) {
            tagStr = tags[0].name ?: ""
        }
        itemView.tagTv.text = tagStr
        itemView.contentTv.text = bean.content
    }

    private fun handleImageNewList(bean: DayNewModel) {
        imageDayNewModelList.clear()
        imageDayNewModelList.add(bean)
        val album = bean.album
        if (album != null && album.isNotEmpty()) {
            imageDayNewModelList.addAll(album)
        }
        dayImageNewListAdapter.setData(imageDayNewModelList)
    }
}
