package com.love.awaker.main;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.love.awaker.R;
import com.love.awaker.base.listener.OnItemClickListener;
import com.love.awaker.data.New;
import com.love.awaker.databinding.ItemMainBinding;
import com.love.awaker.main.holder.MainHolder;

import java.util.List;

/**
 * Copyright ©2017 by Teambition
 */

public class MainAdapter extends RecyclerView.Adapter {

    private OnItemClickListener<New> listener;
    private List<New> news;

    public MainAdapter(OnItemClickListener<New> listener) {
        this.listener = listener;
    }

    public void setData(List<New> news) {
        this.news = news;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMainBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_main, parent, false);
        return new MainHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MainHolder) holder).bind(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news == null ? 0 : news.size();
    }
}
