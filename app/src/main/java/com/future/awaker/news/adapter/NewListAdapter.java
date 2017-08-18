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

import java.util.List;
import java.util.Objects;

/**
 * Copyright ©2017 by ruzhan
 */

public class NewListAdapter extends RecyclerView.Adapter {

    private static final int TYPE_NEW = 1001;

    private OnItemClickListener<News> listener;
    private List<News> news;
    private NewDiffCallBack diffCallBack = new NewDiffCallBack();

    public NewListAdapter(OnItemClickListener<News> listener) {
        this.listener = listener;
    }

    public void setData(List<News> news) {
        if (news == null || news.isEmpty()) {
            return;
        }
        if (this.news == null || this.news.isEmpty()) {
            this.news = news;
            notifyDataSetChanged();

        } else {
            List<News> oldNewses = this.news;
            this.news = news;
            diffCallBack.setData(oldNewses, news);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallBack, false);
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_NEW;
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
        int viewType = getItemViewType(position);
        if (viewType == TYPE_NEW) {
            ((NewHolder) holder).bind(news.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return news == null ? 0 : news.size();
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

    private static class NewDiffCallBack extends IDiffCallBack<News> {

        @Override
        public boolean isItemsTheSame(int oldItemPosition, int newItemPosition) {
            News oldObj = oldData.get(oldItemPosition);
            News newsObj = newData.get(newItemPosition);
            return Objects.equals(oldObj.id, newsObj.id) &&
                    Objects.equals(oldObj.title, newsObj.title);
        }

        @Override
        public boolean isContentsTheSame(int oldItemPosition, int newItemPosition) {
            News oldObj = oldData.get(oldItemPosition);
            News newsObj = newData.get(newItemPosition);
            return Objects.equals(oldObj.id, newsObj.id) &&
                    Objects.equals(oldObj.title, newsObj.title);
        }
    }
}
