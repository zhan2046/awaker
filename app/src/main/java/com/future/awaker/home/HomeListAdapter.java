package com.future.awaker.home;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.future.awaker.R;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.HomeItem;
import com.future.awaker.databinding.ItemHomeListBinding;
import com.future.awaker.home.holder.HomeListHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright ©2017 by Teambition
 */

public class HomeListAdapter extends RecyclerView.Adapter {

    private List<Object> dataList = new ArrayList<>();
    private OnItemClickListener<HomeItem> listener;

    public HomeListAdapter(OnItemClickListener<HomeItem> listener) {
        this.listener = listener;
    }

    public void setData() {
        dataList.clear();
        dataList.addAll(HomeItem.getList());
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemHomeListBinding homeListBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_home_list, parent, false);
        HomeListHolder homeListHolder = new HomeListHolder(homeListBinding, listener);
        return homeListHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((HomeListHolder)holder).bind((HomeItem) dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public int getSpanSize(int position) {
        return 1;
    }
}
