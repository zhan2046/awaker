package com.future.awaker.video;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.future.awaker.R;
import com.future.awaker.base.EmptyHolder;
import com.future.awaker.base.IDiffCallBack;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.Video;
import com.future.awaker.databinding.ItemVideoListBinding;
import com.future.awaker.imageloader.ImageLoader;

import java.util.List;
import java.util.Objects;

/**
 * Copyright ©2017 by Teambition
 */

public class VideoListAdapter extends RecyclerView.Adapter {

    private static final int TYPE_LOADING = 1000;
    private static final int TYPE_VIDEO = 1001;

    private OnItemClickListener<Video> listener;
    private List<Video> videos;
    private VideoDiffCallBack diffCallBack = new VideoDiffCallBack();
    private EmptyHolder emptyHolder;

    public VideoListAdapter(OnItemClickListener<Video> listener) {
        this.listener = listener;
    }

    public void setData(List<Video> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (this.videos == null || this.videos.isEmpty()) {
            this.videos = list;
            notifyDataSetChanged();

        } else {
            List<Video> oldVideos = this.videos;
            this.videos = list;
            diffCallBack.setData(oldVideos, list);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallBack, false);
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == videos.size()) {
            return TYPE_LOADING;
        }
        return TYPE_VIDEO;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOADING) {
            emptyHolder = new EmptyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load, parent, false));
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
            ((VideoHolder) holder).bind(videos.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size() + 1;
    }

    public void setEmpty(boolean isEmpty) {
        emptyHolder.itemView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }

    public static class VideoHolder extends RecyclerView.ViewHolder {

        public ItemVideoListBinding binding;

        public VideoHolder(ItemVideoListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Video bean) {
            binding.setVideoItem(bean);
            String url = bean.cover == null ? "" : bean.cover;
            ImageLoader.get().loadThumb(binding.iconIv, url);
        }
    }

    private static class VideoDiffCallBack extends IDiffCallBack<Video> {

        @Override
        public boolean isItemsTheSame(int oldItemPosition, int newItemPosition) {
            Video oldObj = oldData.get(oldItemPosition);
            Video newObj = newData.get(newItemPosition);
            return Objects.equals(oldObj.id, newObj.id) &&
                    Objects.equals(oldObj.title, newObj.title);
        }

        @Override
        public boolean isContentsTheSame(int oldItemPosition, int newItemPosition) {
            Video oldObj = oldData.get(oldItemPosition);
            Video newObj = newData.get(newItemPosition);
            return Objects.equals(oldObj.id, newObj.id) &&
                    Objects.equals(oldObj.title, newObj.title);
        }
    }
}
