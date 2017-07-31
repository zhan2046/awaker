package com.future.awaker.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.future.awaker.R;

/**
 * Copyright ©2017 by Teambition
 */

public abstract class BaseListFragment<VB extends ViewDataBinding> extends BaseFragment<VB>
        implements SwipeRefreshLayout.OnRefreshListener {

    protected SwipeRefreshLayout swipeRefresh;
    protected RecyclerView recyclerView;
    protected BaseListViewModel listViewModel;

    protected boolean isStopRefresh;
    protected boolean isStopLoadMore;

    private int[] lastPositions;

    @Override
    protected int getLayout() {
        return R.layout.frag_base_list;
    }

    protected abstract void initData();


    public void setListViewModel(BaseListViewModel viewModel) {
        listViewModel = viewModel;
        addRunStatusChangeCallBack(listViewModel);
    }

    @Override
    protected void onCreateBindView() {
        swipeRefresh = findViewById(binding.getRoot(), R.id.swipe_refresh);
        recyclerView = findViewById(binding.getRoot(), R.id.recycler_view);

        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isLoadMore(recyclerView, newState)) {
                    onLoadMore();
                }
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
    }

    private boolean isLoadMore(RecyclerView recyclerView, int newState) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            int lastPosition = 0;
            if (layoutManager instanceof LinearLayoutManager) {
                lastPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager =
                        (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastPosition = findMax(lastPositions);
            }

            int adapterCount = adapter.getItemCount();
            int refreshPosition = adapterCount - 1;
            return lastPosition > 0 && lastPosition >= refreshPosition &&
                    (newState == RecyclerView.SCROLL_STATE_IDLE ||
                            newState == RecyclerView.SCROLL_STATE_SETTLING);
        }
        return false;
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    public void onRefresh() {
        if (listViewModel == null) {
            return;
        }
        if (isStopRefresh) {
            listViewModel.refreshing.set(false);
            return;
        }
        listViewModel.fetchData(true);
    }

    public void onLoadMore() {
        if (isStopLoadMore || listViewModel == null) {
            return;
        }
        listViewModel.fetchData(false);
    }

    @Override
    protected void runStatusChange() {
        if (listViewModel != null &&
                !listViewModel.isRunning.get()) { // 网络请求结束隐藏下拉刷新
            listViewModel.refreshing.set(false);
        }
    }
}