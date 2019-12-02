package com.ruzhan.day.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.awaker.common.OnItemClickListener
import com.ruzhan.day.R
import com.ruzhan.day.adapter.holder.DayImageNewListHolder
import com.ruzhan.day.model.DayNewModel

class DayImageNewListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList = ArrayList<Any>()
    var onItemClickListener: OnItemClickListener<DayNewModel>? = null

    fun setData(list: List<DayNewModel>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DayImageNewListHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.day_item_image_day_new_list, parent, false),
                onItemClickListener)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DayImageNewListHolder).bind(dataList[position] as DayNewModel)
    }
}