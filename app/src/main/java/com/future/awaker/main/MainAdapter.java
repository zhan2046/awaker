package com.future.awaker.main;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.future.awaker.R;
import com.future.awaker.base.IDiffCallBack;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.New;
import com.future.awaker.databinding.ItemMainBinding;
import com.future.awaker.imageloader.ImageLoader;

import java.util.List;

/**
 * Copyright ©2017 by Teambition
 */

public class MainAdapter extends RecyclerView.Adapter {

    private OnItemClickListener<New> listener;
    private List<New> news;
    private NewDiffCallBack diffCallBack = new NewDiffCallBack();

    public MainAdapter(OnItemClickListener<New> listener) {
        this.listener = listener;
    }

    public void setData(List<New> news) {
        if (news == null || news.isEmpty()) {
            return;
        }
        if (this.news == null || this.news.isEmpty()) {
            this.news = news;
            notifyDataSetChanged();

        } else {
            List<New> oldNews = this.news;
            this.news = news;
            diffCallBack.setData(oldNews, news);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallBack, false);
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMainBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_main, parent, false);
        MainHolder mainHolder = new MainHolder(binding);
        binding.setHolder(mainHolder);
        binding.setListener(listener);
        return mainHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MainHolder) holder).bind(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news == null ? 0 : news.size();
    }

    public static class MainHolder extends RecyclerView.ViewHolder {

        public ItemMainBinding binding;

        public MainHolder(ItemMainBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(New bean) {
            binding.setNewItem(bean);
            ImageLoader.get().loadThumb(itemView.getContext(), bean.cover_url.ori, binding.iconIv);
        }
    }

    private class NewDiffCallBack extends IDiffCallBack<New> {

        @Override
        public boolean isItemsTheSame(int oldItemPosition, int newItemPosition) {
            New oldObj = oldData.get(oldItemPosition);
            New newObj = newData.get(newItemPosition);
            return oldObj.id.equals(newObj.id) && oldObj.title.equals(newObj.title);
        }

        @Override
        public boolean isContentsTheSame(int oldItemPosition, int newItemPosition) {
            New oldObj = oldData.get(oldItemPosition);
            New newObj = newData.get(newItemPosition);
            return oldObj.id.equals(newObj.id) && oldObj.title.equals(newObj.title);
        }
    }
}
