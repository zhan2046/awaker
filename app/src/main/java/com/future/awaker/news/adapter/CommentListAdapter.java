package com.future.awaker.news.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.future.awaker.R;
import com.future.awaker.base.EmptyHolder;
import com.future.awaker.base.IDiffCallBack;
import com.future.awaker.base.viewmodel.BaseListViewModel;
import com.future.awaker.data.Comment;
import com.future.awaker.databinding.ItemLoadBinding;
import com.future.awaker.databinding.ItemNewDetailCommentBinding;
import com.future.awaker.news.adapter.holder.NewDetailCommentHolder;

import java.util.List;
import java.util.Objects;

/**
 * Copyright ©2017 by Teambition
 */

public class CommentListAdapter extends RecyclerView.Adapter {

    private static final int TYPE_MORE = 1000;
    private static final int TYPE_ITEM = 1001;

    private List<Comment> commentList;
    private BaseListViewModel viewModel;
    private CommentDiffCallBack diffCallBack = new CommentDiffCallBack();

    public CommentListAdapter(BaseListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setData(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return;
        }

        if (commentList == null) {
            commentList = comments;
            notifyDataSetChanged();

        } else {
            List<Comment> oldCommentList = commentList;
            commentList = comments;
            diffCallBack.setData(oldCommentList, comments);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallBack, false);
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= commentList.size()) {
            return TYPE_MORE;
        }
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            ItemNewDetailCommentBinding binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_new_detail_comment, parent, false);
            return new NewDetailCommentHolder(binding);

        } else if (viewType == TYPE_MORE) {
            ItemLoadBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_load, parent, false);
            binding.setViewModel(viewModel);
            return new EmptyHolder(binding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_ITEM) {
            ((NewDetailCommentHolder) holder).bind(commentList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return commentList == null ? 0 : commentList.size() + 1;
    }

    private static class CommentDiffCallBack extends IDiffCallBack<Comment> {

        @Override
        public boolean isItemsTheSame(int oldItemPosition, int newItemPosition) {
            Comment oldObj = oldData.get(oldItemPosition);
            Comment newsObj = newData.get(newItemPosition);
            return Objects.equals(oldObj.id, newsObj.id) &&
                    Objects.equals(oldObj.content, newsObj.content);
        }

        @Override
        public boolean isContentsTheSame(int oldItemPosition, int newItemPosition) {
            Comment oldObj = oldData.get(oldItemPosition);
            Comment newsObj = newData.get(newItemPosition);
            return Objects.equals(oldObj.id, newsObj.id) &&
                    Objects.equals(oldObj.content, newsObj.content);
        }
    }
}
