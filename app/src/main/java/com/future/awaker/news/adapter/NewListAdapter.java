package com.future.awaker.news.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.future.awaker.R;
import com.future.awaker.base.IDiffCallBack;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.News;
import com.future.awaker.databinding.ItemNewListGridBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Copyright ©2017 by ruzhan
 */

public class NewListAdapter extends RecyclerView.Adapter {

    private OnItemClickListener<News> listener;

    private List<Object> newsList = new ArrayList<>();
    private List<Object> oldNewsList = new ArrayList<>();

    private NewDiffCallBack diffCallBack = new NewDiffCallBack();

    public NewListAdapter(OnItemClickListener<News> listener) {
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

    public void setUpdateData(List<News> list) {
        if (list == null) {
            return;
        }
        oldNewsList.clear();
        oldNewsList.addAll(newsList);

        newsList.clear();
        newsList.addAll(list);

        diffCallBack.setData(oldNewsList, newsList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallBack, false);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemNewListGridBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_new_list_grid, parent, false);
        NewHolder newHolder = new NewHolder(binding);
        binding.setHolder(newHolder);
        binding.setListener(listener);
        return newHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((NewHolder) holder).bind((News) newsList.get(position));
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
        }

        public void bind(News bean) {
            String commentStr = String.format(itemView.getResources()
                    .getString(R.string.comment_count), bean.comment);
            binding.commentTv.setText(commentStr);


            binding.setNewsItem(bean);
            binding.executePendingBindings();
        }
    }

    private static class NewDiffCallBack extends IDiffCallBack<Object> {

        @Override
        public boolean isItemsTheSame(int oldItemPosition, int newItemPosition) {
            Object oldObj = oldData.get(oldItemPosition);
            Object newsObj = newData.get(newItemPosition);

            if (oldObj instanceof News && newsObj instanceof News) {
                News oldNews = (News) oldObj;
                News newNews = (News) newsObj;
                return Objects.equals(oldNews.id, newNews.id) &&
                        Objects.equals(oldNews.title, newNews.title);
            }
            return false;
        }

        @Override
        public boolean isContentsTheSame(int oldItemPosition, int newItemPosition) {
            Object oldObj = oldData.get(oldItemPosition);
            Object newsObj = newData.get(newItemPosition);

            if (oldObj instanceof News && newsObj instanceof News) {
                News oldNews = (News) oldObj;
                News newNews = (News) newsObj;
                return Objects.equals(oldNews.id, newNews.id) &&
                        Objects.equals(oldNews.title, newNews.title);
            }
            return false;
        }
    }
}
