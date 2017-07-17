package com.future.awaker.video;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.future.awaker.R;
import com.future.awaker.base.EmptyHolder;
import com.future.awaker.base.IDiffCallBack;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.Special;
import com.future.awaker.databinding.ItemLoadBinding;
import com.future.awaker.databinding.ItemVideoListBinding;

import java.util.List;
import java.util.Objects;

/**
 * Copyright ©2017 by Teambition
 */

public class VideoListAdapter extends RecyclerView.Adapter {

    private static final int TYPE_LOADING = 1000;
    private static final int TYPE_VIDEO = 1001;

    private VideoViewModel videoViewModel;
    private OnItemClickListener<Special> listener;
    private List<Special> specials;
    private VideoDiffCallBack diffCallBack = new VideoDiffCallBack();

    public VideoListAdapter(VideoViewModel videoViewModel, OnItemClickListener<Special> listener) {
        this.videoViewModel = videoViewModel;
        this.listener = listener;
    }

    public VideoViewModel getVideoViewModel() {
        return videoViewModel;
    }

    public void setData(List<Special> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (this.specials == null || this.specials.isEmpty()) {
            this.specials = list;
            notifyDataSetChanged();

        } else {
            List<Special> oldSpecials = this.specials;
            this.specials = list;
            diffCallBack.setData(oldSpecials, list);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallBack, false);
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == specials.size()) {
            return TYPE_LOADING;
        }
        return TYPE_VIDEO;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOADING) {
            ItemLoadBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_load, parent, false);
            binding.setViewModel(videoViewModel);
            EmptyHolder emptyHolder = new EmptyHolder(binding);
            return emptyHolder;

        } else {
            ItemVideoListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_video_list, parent, false);
            VideoHolder holder = new VideoHolder(binding);
            binding.setHolder(holder);
            binding.setListener(listener);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_VIDEO) {
            ((VideoHolder) holder).bind(specials.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return specials == null ? 0 : specials.size() + 1;
    }

    public static class VideoHolder extends RecyclerView.ViewHolder {

        public ItemVideoListBinding binding;

        public VideoHolder(ItemVideoListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Special bean) {
            binding.setSpecialItem(bean);
            binding.executePendingBindings();
        }
    }

    private static class VideoDiffCallBack extends IDiffCallBack<Special> {

        @Override
        public boolean isItemsTheSame(int oldItemPosition, int newItemPosition) {
            Special oldObj = oldData.get(oldItemPosition);
            Special newObj = newData.get(newItemPosition);
            return Objects.equals(oldObj.id, newObj.id) &&
                    Objects.equals(oldObj.title, newObj.title);
        }

        @Override
        public boolean isContentsTheSame(int oldItemPosition, int newItemPosition) {
            Special oldObj = oldData.get(oldItemPosition);
            Special newObj = newData.get(newItemPosition);
            return Objects.equals(oldObj.id, newObj.id) &&
                    Objects.equals(oldObj.title, newObj.title);
        }
    }
}
