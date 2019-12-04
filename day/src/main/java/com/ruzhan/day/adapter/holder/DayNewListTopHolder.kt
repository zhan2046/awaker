package com.ruzhan.day.adapter.holder

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ruzhan.day.helper.NumberHelper
import com.ruzhan.day.base.OnItemClickListener
import com.ruzhan.day.R
import com.ruzhan.day.adapter.DayImageNewListAdapter
import com.ruzhan.day.db.entity.DayNew
import com.ruzhan.day.db.entity.DayNewChild
import com.ruzhan.font.FontHelper
import com.ruzhan.imageloader.glide.ImageLoader
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.day_item_day_new_list_top.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("SimpleDateFormat")
class DayNewListTopHolder(itemView: View, listener: OnItemClickListener<DayNew>?) :
        RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View?
        get() = itemView

    companion object {
        private const val INDEX_YEAR = 0
        private const val INDEX_MONTH = 1
        private const val INDEX_DAY = 2
        private const val DATE_FORMAT = "yyyy/MM/dd/"
    }

    private lateinit var dayNew: DayNew
    private val simpleTime = SimpleDateFormat(DATE_FORMAT)
    private val dayImageNewListAdapter = DayImageNewListAdapter()
    private val imageDayNewModelList = ArrayList<Any>()

    init {
        firstTitleTv.typeface = FontHelper.get().boldFontTypeface
        tagTv.typeface = FontHelper.get().lightFontTypeface
        contentTv.typeface = FontHelper.get().lightFontTypeface

        if (listener != null) {
            itemView.setOnClickListener { view ->
                listener.onItemClick(view, adapterPosition, dayNew)
            }
        }
        val layoutManager = LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL,
                false)
        newListRecyclerView.layoutManager = layoutManager
        newListRecyclerView.adapter = dayImageNewListAdapter
        dayImageNewListAdapter.onItemClickListener = object : OnItemClickListener<Any> {
            override fun onItemClick(itemView: View, position: Int, bean: Any) {
                if (bean is DayNew) {
                    ImageLoader.get().load(picIv, bean.cover_landscape)
                    contentTv.text = bean.content
                    dayImageNewListAdapter.notifyDataSetChanged()
                } else if (bean is DayNewChild) {
                    ImageLoader.get().load(picIv, bean.cover_landscape)
                    contentTv.text = bean.content
                    dayImageNewListAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun bind(bean: DayNew) {
        dayNew = bean
        ImageLoader.get().load(picIv, bean.cover_landscape)
        handleContentText(bean)
        handleTimeTitle(bean)
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

    private fun handleTimeTitle(bean: DayNew) {
        val titleText = getCurrentTimeStr(bean)
        firstTimeTv.text = titleText
        firstTitleTv.text = bean.title
    }

    private fun getCurrentTimeStr(bean: DayNew): String {
        var titleText = bean.title
        val currentTime = simpleTime.format(Date(bean.pubdate_timestamp * 1000))
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
