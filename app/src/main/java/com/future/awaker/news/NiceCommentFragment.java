package com.future.awaker.news;

import com.future.awaker.R;
import com.future.awaker.base.BaseListFragment;
import com.future.awaker.databinding.FragNiceCommentBinding;

/**
 * Copyright ©2017 by Teambition
 */

public class NiceCommentFragment extends BaseListFragment<FragNiceCommentBinding> {

    private NiceCommentViewModel viewModel = new NiceCommentViewModel();

    public static NiceCommentFragment newInstance() {
        return new NiceCommentFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_nice_comment;
    }

    @Override
    protected void initData() {
        setListViewModel(viewModel);
        binding.setViewModel(viewModel);

        CommentListAdapter adapter = new CommentListAdapter(viewModel);
        recyclerView.setAdapter(adapter);

        onRefresh();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onDestroy() {
        viewModel.clear();
        super.onDestroy();
    }
}
