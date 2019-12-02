package com.ruzhan.day.adapter.holder

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.awaker.imageloader.ImageLoader
import com.lion.font.FontHelper
import com.ruzhan.common.NumberHelper
import com.ruzhan.common.OnItemClickListener
import com.ruzhan.day.R
import com.ruzhan.day.adapter.DayImageNewListAdapter
import com.ruzhan.day.model.DayNewModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.day_item_day_new_list_top.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("SimpleDateFormat")
class DayNewListTopHolder(itemView: View, listener: OnItemClickListener<DayNewModel>?) :
        RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View?
        get() = itemView

    companion object {
        private const val INDEX_YEAR = 0
        private const val INDEX_MONTH = 1
        private const val INDEX_DAY = 2
        private const val DATE_FORMAT = "yyyy/MM/dd/"
    }

    private lateinit var dayNewModel: DayNewModel
    private val simpleTime = SimpleDateFormat(DATE_FORMAT)
    private val dayImageNewListAdapter = DayImageNewListAdapter()
    private val imageDayNewModelList = ArrayList<DayNewModel>()

    init {
        firstTitleTv.typeface = FontHelper.get().boldFontTypeface
        tagTv.typeface = FontHelper.get().lightFontTypeface
        contentTv.typeface = FontHelper.get().lightFontTypeface

        if (listener != null) {
            itemView.setOnClickListener { view ->
                listener.onItemClick(view, adapterPosition, dayNewModel)
            }
        }
        val layoutManager = LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL,
                false)
        newListRecyclerView.layoutManager = layoutManager
        newListRecyclerView.adapter = dayImageNewListAdapter
        dayImageNewListAdapter.onItemClickListener = object : OnItemClickListener<DayNewModel> {
            override fun onItemClick(itemView: View, position: Int, bean: DayNewModel) {
                ImageLoader.get().load(picIv, bean.cover_landscape ?: "")
                contentTv.text = bean.content
                dayImageNewListAdapter.notifyDataSetChanged()
            }
        }
    }

    fun bind(bean: DayNewModel) {
        dayNewModel = bean
        ImageLoader.get().load(picIv, bean.cover_landscape ?: "")
        handleContentText(bean)
        handleTimeTitle(bean)
        handleImageNewList(bean)
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

    private fun handleTimeTitle(bean: DayNewModel) {
        val titleText = getCurrentTimeStr(bean)
        firstTimeTv.text = titleText
        firstTitleTv.text = bean.title
    }

    private fun getCurrentTimeStr(bean: DayNewModel): String {
        var titleText = bean.title ?: ""
        val currentTime = simpleTime.format(Date(bean.pubdate_timestamp.toLong() * 1000))
        val timeList = currentTime.split("/")
        if (timeList.isNotEmpty()) {
            titleText = itemView.resources.getString(R.string.day_tag_year)
            for (index in timeList.indices) {
                var itemText = NumberHelper.getChinaNumber(timeList[index])
                itemText += when (index) {
                    INDEX_YEAR -> itemView.resources.getString(R.string.day_year)
                    INDEX_MONTH -> itemView.resources.getString(R.string.day_month)
                    INDEX_DAY -> itemView.resources.getString(R.string.day_day)
                    else -> ""
                }
                titleText += itemText
            }
        }
        return titleText
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
