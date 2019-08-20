package com.ruzhan.day.adapter.holder

import android.annotation.SuppressLint
import android.view.View
import com.awaker.imageloader.ImageLoader
import com.lion.font.FontHelper
import com.ruzhan.common.NumberHelper
import com.ruzhan.common.OnItemClickListener
import com.ruzhan.day.R
import com.ruzhan.day.model.DayNewModel
import kotlinx.android.synthetic.main.day_item_day_new.view.*
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class DayNewHolder(itemView: View, listener: OnItemClickListener<DayNewModel>?) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    companion object {
        private const val SHOW_TIME_POSITION = 0
        private const val INDEX_YEAR = 0
        private const val INDEX_MONTH = 1
        private const val INDEX_DAY = 2
        private const val DATE_FORMAT = "yyyy/MM/dd/"
    }

    private lateinit var dayNewModel: DayNewModel
    private val simpleTime = SimpleDateFormat(DATE_FORMAT)

    init {
        itemView.titleTv.typeface = FontHelper.get().getBoldTypeface()
        itemView.firstTitleTv.typeface = FontHelper.get().getBoldTypeface()
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
        handleContentText(bean)
        handleTimeTitle(bean)
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

    private fun handleTimeTitle(bean: DayNewModel) {
        if (adapterPosition == SHOW_TIME_POSITION) {
            itemView.titleTv.visibility = View.GONE
            itemView.firstTimeTv.visibility = View.VISIBLE
            itemView.firstTitleTv.visibility = View.VISIBLE

            val titleText = getCurrentTimeStr(bean)
            itemView.firstTimeTv.text = titleText
            itemView.firstTitleTv.text = bean.title
        } else {
            itemView.titleTv.visibility = View.VISIBLE
            itemView.firstTimeTv.visibility = View.GONE
            itemView.firstTitleTv.visibility = View.GONE
            itemView.titleTv.text = bean.title
        }
    }

    private fun getCurrentTimeStr(bean: DayNewModel): String {
        var titleText = bean.title ?: ""
        if (adapterPosition == SHOW_TIME_POSITION) {
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
        }
        return titleText
    }
}