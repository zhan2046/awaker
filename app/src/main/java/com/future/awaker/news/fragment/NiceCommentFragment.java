package com.future.awaker.news.fragment;

import android.view.View;

import com.future.awaker.R;
import com.future.awaker.base.BaseListFragment;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.Comment;
import com.future.awaker.databinding.FragNiceCommentBinding;
import com.future.awaker.news.NewDetailActivity;
import com.future.awaker.news.adapter.HotCommentAdapter;
import com.future.awaker.news.viewmodel.NiceCommentViewModel;

/**
 * Copyright ©2017 by ruzhan
 */

public class NiceCommentFragment extends BaseListFragment<FragNiceCommentBinding>
        implements OnItemClickListener<Comment> {

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

        HotCommentAdapter adapter = new HotCommentAdapter(viewModel, this);
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

    @Override
    public void onItemClick(View view, int position, Comment bean) {
        NewDetailActivity.launch(getActivity(),
                bean.row_id, bean.newstitle.title, "");
    }
}
