package com.ruzhan.common

import android.view.View

interface OnItemClickListener<T> {

    fun onItemClick(itemView: View, position: Int, bean: T)
}