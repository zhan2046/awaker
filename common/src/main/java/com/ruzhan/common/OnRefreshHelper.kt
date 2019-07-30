package com.ruzhan.common

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

object OnRefreshHelper {

    fun setOnRefreshStatusListener(swipeRefresh: androidx.swiperefreshlayout.widget.SwipeRefreshLayout, recyclerView: androidx.recyclerview.widget.RecyclerView,
                                   listener: OnRefreshStatusListener) {
        swipeRefresh.setOnRefreshListener { listener.onRefresh() }
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(recyclerView.context,
                R.color.colorAccent))

        recyclerView.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (isLoadMore(recyclerView, newState)) {
                    listener.onLoadMore()
                }
            }
        })
    }


    fun isLoadMore(recyclerView: androidx.recyclerview.widget.RecyclerView, newState: Int): Boolean {
        val adapter = recyclerView.adapter
        if (adapter != null) {
            val layoutManager = recyclerView.layoutManager
            val lastPosition = layoutManagerToLastPosition(layoutManager!!)
            val adapterCount = adapter.itemCount
            val refreshPosition = adapterCount - 1
            return lastPosition > 0 && lastPosition >= refreshPosition &&
                    (newState == androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE || newState == androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_SETTLING)
        }
        return false
    }

    private fun layoutManagerToLastPosition(layoutManager: androidx.recyclerview.widget.RecyclerView.LayoutManager): Int {
        var lastPosition = 0
        if (layoutManager is androidx.recyclerview.widget.LinearLayoutManager) {
            lastPosition = layoutManager.findLastCompletelyVisibleItemPosition()

        } else if (layoutManager is androidx.recyclerview.widget.StaggeredGridLayoutManager) {
            val lastPositions = IntArray(layoutManager.spanCount)
            layoutManager.findLastVisibleItemPositions(lastPositions)
            lastPosition = findMax(lastPositions)
        }
        return lastPosition
    }

    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }

    interface OnRefreshStatusListener {

        fun onRefresh()

        fun onLoadMore()
    }
}
