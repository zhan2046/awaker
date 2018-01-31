package com.future.awaker.news.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.future.awaker.R;
import com.future.awaker.base.EmptyHolder;
import com.future.awaker.base.IDiffCallBack;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.base.viewmodel.BaseListViewModel;
import com.future.awaker.data.Comment;
import com.future.awaker.databinding.ItemLoadBinding;
import com.future.awaker.databinding.ItemNewHotCommentBinding;
import com.future.awaker.news.adapter.holder.NewHotCommentHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Copyright ©2017 by ruzhan
 */

public class HotCommentAdapter extends RecyclerView.Adapter {

    private static final int TYPE_MORE = 1000;
    private static final int TYPE_ITEM = 1001;

    private static final String LOAD_MORE = "LOAD_MORE";

    private List<Object> commentList = new ArrayList<>();
    private List<Object> oldCommentList = new ArrayList<>();

    private BaseListViewModel viewModel;
    private CommentDiffCallBack diffCallBack = new CommentDiffCallBack();
    private OnItemClickListener<Comment> listener;

    public HotCommentAdapter(BaseListViewModel viewModel,
                             OnItemClickListener<Comment> listener) {
        this.viewModel = viewModel;
        this.listener = listener;
    }

    public void setRefreshData(List<Comment> list) {
        if (list == null) {
            return;
        }
        commentList.clear();
        commentList.addAll(list);
        notifyDataSetChanged();
    }

    public void setUpdateData(List<Comment> list) {
        if (list == null) {
            return;
        }
        oldCommentList.clear();
        oldCommentList.addAll(commentList);

        commentList.clear();
        commentList.addAll(list);

        diffCallBack.setData(oldCommentList, commentList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallBack, false);
        diffResult.dispatchUpdatesTo(this);
    }


    @Override
    public int getItemViewType(int position) {
        Object object = commentList.get(position);
        if (object instanceof String) {
            return TYPE_MORE;
        }
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            ItemNewHotCommentBinding binding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_new_hot_comment, parent, false);
            return new NewHotCommentHolder(binding, listener);

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
            ((NewHotCommentHolder) holder).bind((Comment) commentList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return commentList == null ? 0 : commentList.size();
    }

    private static class CommentDiffCallBack extends IDiffCallBack<Object> {

        @Override
        public boolean isItemsTheSame(int oldItemPosition, int newItemPosition) {
            Object oldObj = oldData.get(oldItemPosition);
            Object newsObj = newData.get(newItemPosition);

            if (oldObj instanceof Comment && newsObj instanceof Comment) {
                Comment oldComment = (Comment) oldObj;
                Comment newComment = (Comment) newsObj;
                return Objects.equals(oldComment.id, newComment.id) &&
                        Objects.equals(oldComment.content, newComment.content);
            }
            return false;
        }

        @Override
        public boolean isContentsTheSame(int oldItemPosition, int newItemPosition) {
            Object oldObj = oldData.get(oldItemPosition);
            Object newsObj = newData.get(newItemPosition);

            if (oldObj instanceof Comment && newsObj instanceof Comment) {
                Comment oldComment = (Comment) oldObj;
                Comment newComment = (Comment) newsObj;
                return Objects.equals(oldComment.id, newComment.id) &&
                        Objects.equals(oldComment.content, newComment.content);
            }
            return false;
        }
    }
}
