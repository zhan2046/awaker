package com.future.awaker.home.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.future.awaker.base.listener.DebouncingOnClickListener;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.HomeItem;
import com.future.awaker.databinding.ItemHomeListBinding;

/**
 * Copyright ©2017 by Teambition
 */

public class HomeListHolder extends RecyclerView.ViewHolder {

    private ItemHomeListBinding binding;
    private HomeItem homeItem;

    public HomeListHolder(ItemHomeListBinding binding,
                          OnItemClickListener<HomeItem> listener) {
        super(binding.getRoot());
        this.binding = binding;
        binding.rootCard.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, getAdapterPosition(), homeItem);
                }
            }
        });
    }

    public void bind(HomeItem bean) {
        homeItem = bean;
        binding.setBean(bean);
        binding.executePendingBindings();
    }
}
