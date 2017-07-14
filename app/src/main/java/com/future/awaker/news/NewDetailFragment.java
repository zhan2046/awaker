package com.future.awaker.news;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.future.awaker.R;
import com.future.awaker.base.BaseListFragment;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.source.NewRepository;
import com.future.awaker.databinding.FragNewDetailBinding;

/**
 * Copyright ©2017 by Teambition
 */

public class NewDetailFragment extends BaseListFragment<FragNewDetailBinding> {

    private static final String NEW_ID = "newId";

    private NewDetailAdapter adapter;

    public static NewDetailFragment newInstance(String newId) {
        Bundle args = new Bundle();
        args.putString(NEW_ID, newId);
        NewDetailFragment fragment = new NewDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_new_detail;
    }

    @Override
    protected int getEmptyLayout() {
        return R.layout.layout_empty;
    }

    @Override
    protected void initData() {
        String newId = getArguments().getString(NEW_ID);
        NewDetailViewModel newDetailViewModel = new NewDetailViewModel(NewRepository.get());
        newDetailViewModel.setNewId(newId);

        setViewModel(newDetailViewModel);
        binding.setViewModel(newDetailViewModel);

        adapter = new NewDetailAdapter();
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("NewApi")
    @Override
    public void onResume() {
        super.onResume();
        if (adapter.getBodyHolder() != null) {
            adapter.getBodyHolder().getWebView().onResume();
        }
        onRefresh();
    }

    @SuppressLint("NewApi")
    @Override
    public void onPause() {
        if (adapter.getBodyHolder() != null) {
            adapter.getBodyHolder().getWebView().onPause();
        }
        super.onPause();

    }

    @SuppressLint("NewApi")
    @Override
    public void onDestroy() {
        if (adapter.getBodyHolder() != null) {
            adapter.getBodyHolder().getWebView().onDestroy();
        }
        super.onDestroy();
    }

    @BindingAdapter({"newDetail"})
    public static void setNewDetail(RecyclerView recyclerView, NewDetail newDetail) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof NewDetailAdapter) {
            ((NewDetailAdapter) adapter).setData(newDetail);
        }
    }

    @Override
    protected void emptyData(boolean isEmpty) {

    }

    @Override
    public void onLoadMore() {
        // new detail not load more
    }

}
