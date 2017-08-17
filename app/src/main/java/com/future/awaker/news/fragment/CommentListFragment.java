package com.future.awaker.news.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.future.awaker.Account;
import com.future.awaker.R;
import com.future.awaker.base.BaseListFragment;
import com.future.awaker.base.listener.DebouncingOnClickListener;
import com.future.awaker.databinding.FragCommentListBinding;
import com.future.awaker.news.adapter.CommentListAdapter;
import com.future.awaker.news.listener.SendCommentListener;
import com.future.awaker.news.viewmodel.CommentViewModel;
import com.future.awaker.util.KeyboardUtils;

/**
 * Copyright ©2017 by Teambition
 */

public class CommentListFragment extends BaseListFragment<FragCommentListBinding> implements SendCommentListener {

    private static final int RESET_EDIT_VALUE = 30;

    private static final String NEW_ID = "newId";

    private CommentViewModel viewModel = new CommentViewModel(this);
    private String newId;

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
        newId = getArguments().getString(NEW_ID);
        viewModel.setNewId(newId);
        setListViewModel(viewModel);
        binding.setViewModel(viewModel);

        binding.toolbar.setTitle(R.string.up_comment_more);
        setToolbar(binding.toolbar);

        CommentListAdapter adapter = new CommentListAdapter(viewModel);
        recyclerView.setAdapter(adapter);

        initListener();

        onRefresh();
    }

    private void initListener() {
        binding.sendIv.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                String content = binding.commentEt.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    return;
                }
                String openId = Account.get().getOpenId();
                viewModel.sendNewsComment(newId, content, openId, null);
            }
        });

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (Math.abs(dy) > RESET_EDIT_VALUE && binding.commentEt.isFocusable()) {
                    binding.commentEt.setText("");
                    binding.commentEt.setFocusable(false);
                    binding.commentEt.setFocusableInTouchMode(true);
                    KeyboardUtils.closeSoftInput(getActivity(), binding.commentEt);
                }
            }
        });

        binding.commentEt.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                binding.commentEt.setFocusable(true);
                binding.commentEt.requestFocus();
            }
        });
    }

    @Override
    public void onDestroy() {
        viewModel.clear();
        super.onDestroy();
    }

    @Override
    public void sendCommentSuc() {
        String str = getResources().getString(R.string.comment_suc);
        Toast.makeText(getContext(), str + "", Toast.LENGTH_LONG).show();
        binding.commentEt.setText("");
        binding.commentEt.setFocusable(false);
        binding.commentEt.setFocusableInTouchMode(true);
        KeyboardUtils.closeSoftInput(getActivity(), binding.commentEt);

        onRefresh();
    }
}
