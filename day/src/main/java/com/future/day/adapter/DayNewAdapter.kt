package com.future.day.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.future.day.base.EmptyHolder
import com.future.day.base.OnItemClickListener
import com.future.day.R
import com.future.day.adapter.holder.DayNewHolder
import com.future.day.adapter.holder.DayNewListHolder
import com.future.day.adapter.holder.DayNewListTopHolder
import com.future.day.adapter.holder.DayNewTopHolder
import com.future.day.db.entity.DayNew

class DayNewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {

        private const val TYPE_DEFAULT = 10000
        private const val TYPE_DAY_NEW_TOP = 10001
        private const val TYPE_DAY_NEW = 10002
        private const val TYPE_DAY_NEW_LIST_TOP = 10003
        private const val TYPE_DAY_NEW_LIST = 10004
    }

    private val dataList = ArrayList<Any>()

    var onItemClickListener: OnItemClickListener<DayNew>? = null

    fun setData(list: List<DayNew>?) {
        if (list != null) {
            dataList.clear()
            dataList.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return if (dataList[position] is DayNew) {
                val dayNew = dataList[position] as DayNew
                val album = dayNew.album
                if (album.isNotEmpty()) {
                    TYPE_DAY_NEW_LIST_TOP
                } else {
                    TYPE_DAY_NEW_TOP
                }
            } else {
                TYPE_DEFAULT
            }
        }
        return when (dataList[position]) {
            is DayNew -> {
                val dayNew = dataList[position] as DayNew
                val album = dayNew.album
                if (album.isNotEmpty()) {
                    TYPE_DAY_NEW_LIST
                } else {
                    val lastBean = dataList[1.coerceAtLeast(position - 1)]
                    if (lastBean is DayNew) {
                        if (lastBean.title == dayNew.title) {
                            TYPE_DAY_NEW
                        } else {
                            TYPE_DAY_NEW_TOP
                        }
                    } else {
                        TYPE_DAY_NEW
                    }
                }
            }
            else -> TYPE_DEFAULT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_DAY_NEW_TOP -> DayNewTopHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.day_item_day_new_top, parent, false),
                    onItemClickListener)

            TYPE_DAY_NEW -> DayNewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.day_item_day_new, parent, false),
                    onItemClickListener)

            TYPE_DAY_NEW_LIST_TOP -> DayNewListTopHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.day_item_day_new_list_top, parent, false),
                    onItemClickListener)

            TYPE_DAY_NEW_LIST -> DayNewListHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.day_item_day_new_list, parent, false),
                    onItemClickListener)

            else -> EmptyHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.common_item_empty, parent, false))
        }
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_DAY_NEW_TOP -> {
                val bean = dataList[position] as DayNew
                val viewHolder = holder as DayNewTopHolder
                viewHolder.bind(bean)
            }
            TYPE_DAY_NEW -> {
                val bean = dataList[position] as DayNew
                val viewHolder = holder as DayNewHolder
                viewHolder.bind(bean)
            }
            TYPE_DAY_NEW_LIST_TOP -> {
                val bean = dataList[position] as DayNew
                val viewHolder = holder as DayNewListTopHolder
                viewHolder.bind(bean)
            }
            TYPE_DAY_NEW_LIST -> {
                val bean = dataList[position] as DayNew
                val viewHolder = holder as DayNewListHolder
                viewHolder.bind(bean)
            }
        }
    }
}