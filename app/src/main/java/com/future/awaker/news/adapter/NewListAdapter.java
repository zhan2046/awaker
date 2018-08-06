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
import com.future.awaker.data.News;
import com.future.awaker.databinding.ItemLoadBinding;
import com.future.awaker.databinding.ItemNewListGridBinding;
import com.future.awaker.databinding.ItemNewListGridLoadBinding;
import com.ruzhan.lion.helper.FontHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Copyright ©2017 by ruzhan
 */

public class NewListAdapter extends RecyclerView.Adapter {

    private static final String LOAD_MORE = "LOAD_MORE";

    private static final int TYPE_NORMAL = 1000;
    private static final int TYPE_LOAD_MORE = 1001;

    private OnItemClickListener<News> listener;

    private List<Object> newsList = new ArrayList<>();

    public NewListAdapter(OnItemClickListener<News> listener) {
        this.listener = listener;
    }

    public void setRefreshData(List<News> list) {
        if (list == null) {
            return;
        }
        newsList.clear();
        newsList.addAll(list);
        if (list.size() >= 10) {
            newsList.add(LOAD_MORE);
        }
        notifyDataSetChanged();
    }

    public void setUpdateData(List<News> list) {
        if (list == null) {
            return;
        }
        newsList.remove(LOAD_MORE);
        newsList.addAll(list);
        if (list.size() >= 10) {
            newsList.add(LOAD_MORE);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Object obj = newsList.get(position);
        if (obj instanceof String) {
            return TYPE_LOAD_MORE;
        }
        return TYPE_NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOAD_MORE) {
            ItemNewListGridLoadBinding binding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_new_list_grid_load, parent, false);
            binding.moreTv.setTypeface(FontHelper.get().getLightTypeface());
            return new EmptyHolder(binding);

        } else {
            ItemNewListGridBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_new_list_grid, parent, false);
            NewHolder newHolder = new NewHolder(binding);
            binding.setHolder(newHolder);
            binding.setListener(listener);
            return newHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_LOAD_MORE) {

        } else {
            ((NewHolder) holder).bind((News) newsList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewHolder extends RecyclerView.ViewHolder {

        public ItemNewListGridBinding binding;

        public NewHolder(ItemNewListGridBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.titleTv.setTypeface(FontHelper.get().getLightTypeface());
            binding.categoryTv.setTypeface(FontHelper.get().getLightTypeface());
            binding.commentTv.setTypeface(FontHelper.get().getLightTypeface());
        }

        public void bind(News bean) {
            String commentStr = String.format(itemView.getResources()
                    .getString(R.string.comment_count), bean.comment);
            binding.commentTv.setText(commentStr);


            binding.setNewsItem(bean);
            binding.executePendingBindings();
        }
    }
}
