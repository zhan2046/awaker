package com.ruzhan.day.adapter.holder

import android.annotation.SuppressLint
import android.view.View
import com.awaker.imageloader.ImageLoader
import com.lion.font.FontHelper
import com.ruzhan.common.NumberHelper
import com.ruzhan.common.OnItemClickListener
import com.ruzhan.day.model.DayNewModel
import kotlinx.android.synthetic.main.day_item_day_new.view.*
import java.text.SimpleDateFormat
import java.util.*

class DayNewHolder(itemView: View, listener: OnItemClickListener<DayNewModel>?) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    companion object {
        private const val DATE_FORMAT = "yyyy/MM/dd/"
    }

    private lateinit var dayNewModel: DayNewModel
    @SuppressLint("SimpleDateFormat")
    private val simpleTime = SimpleDateFormat(DATE_FORMAT)

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
        val titleText = getCurrentTimeStr(bean)
        itemView.titleTv.text = titleText
        itemView.tagTv.text = tagStr
        itemView.contentTv.text = bean.content
    }

    private fun getCurrentTimeStr(bean: DayNewModel): String {
        var titleText = bean.title ?: ""
        if (adapterPosition == 0) {
            val currentTime = simpleTime.format(Date(bean.pubdate_timestamp.toLong() * 1000))
            val timeList = currentTime.split("/")
            if (timeList.isNotEmpty()) {
                titleText = "公元"
                for (index in timeList.indices) {
                    var itemText = NumberHelper.getChinaNumber(timeList[index])
                    itemText += when (index) {
                        0 -> "年"
                        1 -> "月"
                        2 -> "日"
                        else -> ""
                    }
                    titleText += itemText
                }
            }
        }
        return titleText
    }
}