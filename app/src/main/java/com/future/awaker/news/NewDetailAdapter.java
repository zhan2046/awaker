package com.future.awaker.news;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.future.awaker.R;
import com.future.awaker.data.NewDetail;
import com.future.awaker.databinding.ItemNewDetailBodyBinding;
import com.future.awaker.databinding.ItemNewDetailHeaderBinding;
import com.future.awaker.imageloader.ImageLoader;

/**
 * Copyright ©2017 by Teambition
 */

public class NewDetailAdapter extends RecyclerView.Adapter {

    // WebView图片适配
    private static final String IMG = "img";
    private static final String IMG_WIDTH_AUTO = "<img style='max-width:90%;height:auto;'";

    private static final int TYPE_HEADER = 1000;
    private static final int TYPE_BODY = 1001;

    private NewDetail newDetail;

    public void setData(NewDetail newDetail) {
        if (newDetail == null) {
            return;
        }
        this.newDetail = newDetail;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_BODY;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            ItemNewDetailHeaderBinding headerBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_new_detail_header, parent, false);
            HeaderHolder headerHolder = new HeaderHolder(headerBinding);

            return headerHolder;
        } else {
            ItemNewDetailBodyBinding bodyBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_new_detail_body, parent, false);
            BodyHolder bodyHolder = new BodyHolder(bodyBinding);

            return bodyHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_HEADER) {
            ((HeaderHolder) holder).bind(newDetail);
        } else {
            ((BodyHolder) holder).bind(newDetail);
        }
    }

    @Override
    public int getItemCount() {
        return newDetail == null ? 0 : 2;
    }

    private static class HeaderHolder extends RecyclerView.ViewHolder {

        private ItemNewDetailHeaderBinding binding;

        public HeaderHolder(ItemNewDetailHeaderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NewDetail newDetail) {
            binding.userTv.setText(newDetail.user == null ? "" : newDetail.user.nickname);
            String userUrl = newDetail.user == null ? "" : newDetail.user.avatar128;
            ImageLoader.get().loadThumb(binding.userIv, userUrl);

            String headerUrl = newDetail.cover_url == null ? "" : newDetail.cover_url.ori;
            ImageLoader.get().loadThumb(binding.iconIv, headerUrl);
        }
    }

    private static class BodyHolder extends RecyclerView.ViewHolder {

        private ItemNewDetailBodyBinding bodyBinding;

        public BodyHolder(ItemNewDetailBodyBinding bodyBinding) {
            super(bodyBinding.getRoot());
            this.bodyBinding = bodyBinding;

            WebSettings settings = bodyBinding.bodyWebView.getSettings();
            settings.setJavaScriptEnabled(false);
            settings.setSupportZoom(false);
            settings.setBuiltInZoomControls(false);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            settings.setDefaultFontSize(16);
        }

        public void bind(NewDetail newDetail) {
            String htmlData = newDetail.content.replace(IMG, IMG_WIDTH_AUTO);
            bodyBinding.bodyWebView.loadDataWithBaseURL(null, htmlData, "text/html", "utf-8", null);
        }
    }
}
