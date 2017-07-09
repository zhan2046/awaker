package com.love.awaker.main;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.love.awaker.R;
import com.love.awaker.base.listener.OnItemClickListener;
import com.love.awaker.data.New;
import com.love.awaker.databinding.ItemMainBinding;
import com.love.awaker.imageloader.ImageLoader;
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

    @SuppressWarnings("unchecked")
    @BindingAdapter("app:news")
    public static void setNews(RecyclerView recyclerView, List<New> news) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter  instanceof MainAdapter) {
            ((MainAdapter)adapter).setData(news);
        }
    }

    public void setData(List<New> news) {
        this.news = news;
        notifyDataSetChanged();
        Log.i("news","setDataed...");
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
            ImageLoader.get().load(itemView.getContext(), bean.cover_url.ori, binding.iconIv);
        }
    }
}
