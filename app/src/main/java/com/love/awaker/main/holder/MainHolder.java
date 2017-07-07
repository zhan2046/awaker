package com.love.awaker.main.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.love.awaker.base.listener.CustomOnClickListener;
import com.love.awaker.base.listener.OnItemClickListener;
import com.love.awaker.data.New;
import com.love.awaker.databinding.ItemMainBinding;

/**
 * Copyright ©2017 by Teambition
 */

public class MainHolder extends RecyclerView.ViewHolder {

    private ItemMainBinding binding;
    private New newItem;

    public MainHolder(ItemMainBinding binding, OnItemClickListener<New> listener) {
        super(binding.getRoot());
        this.binding = binding;
        binding.newCardView.setOnClickListener(new CustomOnClickListener() {
            @Override
            public void doClick(View v) {
                listener.onItemClick(itemView, getAdapterPosition(), newItem);
            }
        });
    }

    public void bind(New bean) {
        newItem = bean;
        binding.setNewItem(newItem);
        binding.executePendingBindings();
    }
}
