package com.future.awaker.helper;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;


public class LoadMoreHelper {

    private LoadMoreHelper() {
    }

    public static boolean isLoadMore(RecyclerView recyclerView, int newState) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int lastPosition = layoutManagerToLastPosition(layoutManager);
            int adapterCount = adapter.getItemCount();
            int refreshPosition = adapterCount - 1;
            return lastPosition > 0 && lastPosition >= refreshPosition &&
                    (newState == RecyclerView.SCROLL_STATE_IDLE ||
                            newState == RecyclerView.SCROLL_STATE_SETTLING);
        }
        return false;
    }

    private static int layoutManagerToLastPosition(RecyclerView.LayoutManager layoutManager) {
        int lastPosition = 0;
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();

        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager =
                    (StaggeredGridLayoutManager) layoutManager;
            int[] lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
            staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
            lastPosition = findMax(lastPositions);
        }
        return lastPosition;
    }

    private static int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
