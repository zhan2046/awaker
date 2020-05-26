package com.future.movie.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.future.movie.R
import com.future.movie.db.entity.VideoItem
import com.future.movie.detail.adapter.MovieDetailAdapter

class VideoItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val dataList = ArrayList<Any>()
    private var videoTitlePosition = 0
    private var defaultSpace = 0
    private var defaultLeft = 0

    init {
        defaultSpace = context.resources.getDimension(R.dimen.space_small_4).toInt()
        defaultLeft = context.resources.getDimension(R.dimen.space_small_8).toInt()
    }

    fun setData(list: List<Any>) {
        dataList.clear()
        dataList.addAll(list)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        if (position >= 0 && position <= dataList.size - 1) {
            val obj = dataList[position]
            if (obj is String) {
                if (obj == MovieDetailAdapter.VIDEO_TITLE) {
                    videoTitlePosition = position
                }
            } else if (obj is VideoItem) {
                if (position % 2 == 0) {
                    outRect.set((defaultLeft * 0.5).toInt(), defaultSpace,
                        defaultLeft, defaultSpace)
                } else {
                    outRect.set(defaultLeft, defaultSpace,
                        (defaultLeft * 0.5).toInt(), defaultSpace)
                }
            } else {
                outRect.set(0, 0, 0, 0)
            }
        }
    }
}