package com.future.awaker.news.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.future.awaker.R;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.News;
import com.future.awaker.databinding.ItemNewListGridBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright ©2017 by ruzhan
 */

public class OtherNewListAdapter extends RecyclerView.Adapter {

    private OnItemClickListener<News> listener;

    private List<Object> newsList = new ArrayList<>();

    public OtherNewListAdapter(OnItemClickListener<News> listener) {
        this.listener = listener;
    }

    public void setRefreshData(List<News> list) {
        if (list == null) {
            return;
        }
        newsList.clear();
        newsList.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemNewListGridBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.awaker_article_item_new_list_grid, parent, false);
        NewListAdapter.NewHolder newHolder = new NewListAdapter.NewHolder(binding);
        binding.setHolder(newHolder);
        binding.setListener(listener);
        return newHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NewListAdapter.NewHolder) holder).bind((News) newsList.get(position));
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
