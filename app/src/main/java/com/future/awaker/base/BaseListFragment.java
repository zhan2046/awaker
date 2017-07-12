package com.future.awaker.base;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.future.awaker.R;

/**
 * Copyright ©2017 by Teambition
 */

public abstract class BaseListFragment<VB extends ViewDataBinding> extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final int EMPTY_LAYOUT = -1;

    protected VB binding;
    protected BaseListViewModel baseListViewModel;

    protected SwipeRefreshLayout swipeRefresh;
    protected RecyclerView recyclerView;

    protected FrameLayout containerFl;
    protected ViewStub viewStub;
    private boolean isInflate;

    protected boolean isRefresh = true;
    protected boolean isStopRefresh;
    protected boolean isStopLoadMore;
    protected int page = 1;

    private RunCallBack runCallBack;

    @SuppressWarnings("unchecked")
    protected <T> T findViewById(View view, int id) {
        return (T) view.findViewById(id);
    }

    protected int getLayout() {
        return R.layout.frag_base_list;
    }

    protected int getEmptyLayout() {
        return EMPTY_LAYOUT;
    }

    public void setBaseListViewModel(BaseListViewModel baseListViewModel) {
        this.baseListViewModel = baseListViewModel;
        runCallBack = new RunCallBack();
        baseListViewModel.isRunning.addOnPropertyChangedCallback(runCallBack);
    }

    protected abstract void initData();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        View rootView = binding.getRoot();
        swipeRefresh = findViewById(rootView, R.id.swipe_refresh);
        containerFl = findViewById(rootView, R.id.container_fl);
        recyclerView = findViewById(rootView, R.id.recycler_view);

        initEmptyView();

        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorAccent));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isLoadMore(recyclerView, newState)) {
                    onLoadMore();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();

        //init data finish, end call refresh
        onRefresh();
    }

    @Override
    public void onDestroyView() {
        if (runCallBack != null) {
            baseListViewModel.isRunning.removeOnPropertyChangedCallback(runCallBack);
        }
        super.onDestroyView();
    }

    private void initEmptyView() {
        if (EMPTY_LAYOUT != getEmptyLayout()) {
            viewStub = new ViewStub(binding.getRoot().getContext(), getEmptyLayout());
            ViewGroup.LayoutParams params =
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
            viewStub.setLayoutParams(params);
            containerFl.addView(viewStub, 0);

            viewStub.setOnInflateListener((stub, inflated) -> isInflate = true);
        }
    }

    protected void showEmptyView(boolean isEmpty) {
        if (isEmpty) {
            viewStub.setVisibility(View.VISIBLE);
        } else if (isInflate) {
            viewStub.setVisibility(View.INVISIBLE);
        }
        recyclerView.setVisibility(isEmpty ? View.INVISIBLE : View.VISIBLE);
    }

    private boolean isLoadMore(RecyclerView recyclerView, int newState) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null) {
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int lastPosition = manager.findLastCompletelyVisibleItemPosition();
            int adapterCount = adapter.getItemCount();
            int refreshPosition = adapterCount - 3;
            return lastPosition > 0 && lastPosition >= refreshPosition &&
                    (newState == RecyclerView.SCROLL_STATE_IDLE ||
                            newState == RecyclerView.SCROLL_STATE_SETTLING);
        }
        return false;
    }

    @Override
    public void onRefresh() {
        if (baseListViewModel == null ||
                baseListViewModel.isRunning.get() ||
                isStopRefresh) {
            setRefreshing(false);
            return;
        }
        baseListViewModel.isEmpty.set(false);

        isRefresh = true;
        page = 1;
        setRefreshing(true);
        fetchData();
    }

    public void onLoadMore() {
        if (baseListViewModel == null ||
                baseListViewModel.isRunning.get() ||
                baseListViewModel.isEmpty.get() ||
                isStopLoadMore) {
            return;
        }

        isRefresh = false;
        page++;
        fetchData();
    }

    protected void setRefreshing(boolean refresh) {
        if (swipeRefresh != null) {
            swipeRefresh.post(() -> {
                if (swipeRefresh != null) {
                    swipeRefresh.setRefreshing(refresh);
                }
            });
        }
    }

    protected void fetchData() {
        if (baseListViewModel != null) {
            baseListViewModel.fetchData(isRefresh, page);
        }
    }

    private class RunCallBack extends Observable.OnPropertyChangedCallback {

        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            if (!baseListViewModel.isRunning.get()) {
                setRefreshing(false);
            }
        }
    }
}
