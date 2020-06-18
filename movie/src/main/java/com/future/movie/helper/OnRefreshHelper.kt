package com.future.movie.helper

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

object OnRefreshHelper {

    fun setOnRefreshStatusListener(swipeRefresh: SwipeRefreshLayout, recyclerView: RecyclerView,
                                   listener: OnRefreshStatusListener, colorRes: Int) {
        swipeRefresh.setOnRefreshListener { listener.onRefresh() }
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(recyclerView.context, colorRes))
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (isLoadMore(recyclerView, newState)) {
                    listener.onLoadMore()
                }
            }
        })
    }

    fun isLoadMore(recyclerView: RecyclerView, newState: Int): Boolean {
        val adapter = recyclerView.adapter
        if (adapter != null) {
            val layoutManager = recyclerView.layoutManager
            val lastPosition = layoutManagerToLastPosition(layoutManager!!)
            val adapterCount = adapter.itemCount
            val refreshPosition = adapterCount - 1
            return lastPosition > 0 && lastPosition >= refreshPosition &&
                (newState == RecyclerView.SCROLL_STATE_IDLE ||
                    newState == RecyclerView.SCROLL_STATE_SETTLING)
        }
        return false
    }

    private fun layoutManagerToLastPosition(layoutManager: RecyclerView.LayoutManager): Int {
        var lastPosition = 0
        if (layoutManager is LinearLayoutManager) {
            lastPosition = layoutManager.findLastCompletelyVisibleItemPosition()

        } else if (layoutManager is StaggeredGridLayoutManager) {
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
