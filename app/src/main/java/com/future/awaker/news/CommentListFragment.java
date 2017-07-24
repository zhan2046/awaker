package com.future.awaker.news;

import android.os.Bundle;

import com.future.awaker.R;
import com.future.awaker.base.BaseListFragment;
import com.future.awaker.databinding.FragCommentListBinding;

/**
 * Copyright ©2017 by Teambition
 */

public class CommentListFragment extends BaseListFragment<FragCommentListBinding> {

    private static final String NEW_ID = "newId";

    private CommentViewModel viewModel = new CommentViewModel();

    public static CommentListFragment newInstance(String newId) {
        Bundle args = new Bundle();
        args.putString(NEW_ID, newId);
        CommentListFragment fragment = new CommentListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_comment_list;
    }

    @Override
    protected void initData() {
        String newId = getArguments().getString(NEW_ID);
        viewModel.setNewId(newId);
        setListViewModel(viewModel);
        binding.setViewModel(viewModel);

        binding.toolbar.setTitle(R.string.up_comment_more);
        setToolbar(binding.toolbar);

        CommentListAdapter adapter = new CommentListAdapter(viewModel);
        recyclerView.setAdapter(adapter);

        onRefresh();
    }

    @Override
    public void onDestroy() {
        viewModel.clear();
        super.onDestroy();
    }
}
