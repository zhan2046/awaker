package com.future.awaker.news;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.future.awaker.R;
import com.future.awaker.base.IDiffCallBack;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.New;
import com.future.awaker.databinding.ItemNewBinding;
import com.future.awaker.imageloader.ImageLoader;

import java.util.List;
import java.util.Objects;

/**
 * Copyright ©2017 by Teambition
 */

public class NewAdapter extends RecyclerView.Adapter {

    private OnItemClickListener<New> listener;
    private List<New> news;
    private NewDiffCallBack diffCallBack = new NewDiffCallBack();

    public NewAdapter(OnItemClickListener<New> listener) {
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
        ItemNewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_new, parent, false);
        NewHolder newHolder = new NewHolder(binding);
        binding.setHolder(newHolder);
        binding.setListener(listener);
        return newHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NewHolder) holder).bind(news.get(position));
    }

    @Override
    public int getItemCount() {
        return news == null ? 0 : news.size();
    }

    public static class NewHolder extends RecyclerView.ViewHolder {

        public ItemNewBinding binding;

        public NewHolder(ItemNewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(New bean) {
            binding.setNewItem(bean);
            String url = bean.cover_url == null ? "" : bean.cover_url.ori;
            ImageLoader.get().loadThumb(binding.iconIv, url);
        }
    }

    private class NewDiffCallBack extends IDiffCallBack<New> {

        @Override
        public boolean isItemsTheSame(int oldItemPosition, int newItemPosition) {
            New oldObj = oldData.get(oldItemPosition);
            New newObj = newData.get(newItemPosition);
            return Objects.equals(oldObj.id, newObj.id) &&
                    Objects.equals(oldObj.title, newObj.title);
        }

        @Override
        public boolean isContentsTheSame(int oldItemPosition, int newItemPosition) {
            New oldObj = oldData.get(oldItemPosition);
            New newObj = newData.get(newItemPosition);
            return Objects.equals(oldObj.id, newObj.id) &&
                    Objects.equals(oldObj.title, newObj.title);
        }
    }
}
