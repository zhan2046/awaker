package com.future.awaker.news;


import android.os.Bundle;

import com.future.awaker.R;
import com.future.awaker.base.BaseListFragment;
import com.future.awaker.databinding.FragNewDetailBinding;

/**
 * Copyright ©2017 by Teambition
 */

public class NewDetailFragment extends BaseListFragment<FragNewDetailBinding> {

    private static final String NEW_ID = "newId";
    private static final String NEW_TITLE = "newTitle";

    private NewDetailAdapter adapter;

    public static NewDetailFragment newInstance(String newId, String newTitle) {
        Bundle args = new Bundle();
        args.putString(NEW_ID, newId);
        args.putString(NEW_TITLE, newTitle);
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
        String newTitle = getArguments().getString(NEW_TITLE);

        setToolbar(binding.toolbar);

        NewDetailViewModel newDetailViewModel = new NewDetailViewModel();
        newDetailViewModel.setNewId(newId);
        newDetailViewModel.setTitle(newTitle);

        setViewModel(newDetailViewModel);
        binding.setViewModel(newDetailViewModel);

        adapter = new NewDetailAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {

        super.onPause();

    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }


    @Override
    public void onLoadMore() {
        // new detail not load more
    }
}
