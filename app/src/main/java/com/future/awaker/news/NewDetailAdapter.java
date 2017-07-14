package com.future.awaker.news;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.Toast;

import com.future.awaker.Application;
import com.future.awaker.R;
import com.future.awaker.data.NewDetail;
import com.future.awaker.databinding.ItemNewDetailBodyBinding;
import com.future.awaker.databinding.ItemNewDetailHeaderBinding;
import com.future.awaker.imageloader.ImageLoader;

import im.delight.android.webview.AdvancedWebView;

/**
 * Copyright ©2017 by Teambition
 */

public class NewDetailAdapter extends RecyclerView.Adapter {

    // WebView图片适配
    private static final String IMG = "<img";
    private static final String IMG_WIDTH_AUTO = "<img style='max-width:100%;height:auto;'";

    private static final int TYPE_HEADER = 1000;
    private static final int TYPE_BODY = 1001;

    private NewDetail newDetail;
    private BodyHolder bodyHolder;

    public void setData(NewDetail newDetail) {
        if (newDetail == null) {
            return;
        }
        this.newDetail = newDetail;
        notifyDataSetChanged();
    }

    public BodyHolder getBodyHolder() {
        return bodyHolder;
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
            bodyHolder = new BodyHolder(bodyBinding);

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

    public class BodyHolder extends RecyclerView.ViewHolder {

        private ItemNewDetailBodyBinding bodyBinding;
        private AdvancedWebView webView;

        public BodyHolder(ItemNewDetailBodyBinding bodyBinding) {
            super(bodyBinding.getRoot());
            this.bodyBinding = bodyBinding;
            webView = bodyBinding.bodyWebView;

            Activity activity = (Activity) itemView.getContext();

            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webView.addJavascriptInterface(new JavascriptInterface(activity), "imagelistner");
            webView.setListener(activity, new AdvancedWebView.Listener() {
                @Override
                public void onPageStarted(String url, Bitmap favicon) {

                }

                @Override
                public void onPageFinished(String url) {
                    addImageClickListener();
                }

                @Override
                public void onPageError(int errorCode, String description, String failingUrl) {
                    Toast.makeText(Application.get(), "请检查您的网络设置", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

                }

                @Override
                public void onExternalPageRequest(String url) {

                }
            });
//
//            webView.setOnTouchListener((v, ev) -> {
//                ((WebView)v).requestDisallowInterceptTouchEvent(true);
//                return false;
//            });
        }

        private void addImageClickListener() {
            // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
            webView.loadUrl("javascript:(function(){" +
                    "var objs = document.getElementsByTagName(\"img\"); " +
                    "for(var i=0;i<objs.length;i++)  " +
                    "{"
                    + "    objs[i].onclick=function()  " +
                    "    {  "
                    + "        window.imagelistner.openImage(this.src);  " +
                    "    }  " +
                    "}" +
                    "})()");
        }

        public AdvancedWebView getWebView() {
            return webView;
        }

        public void bind(NewDetail newDetail) {
            String htmlData = newDetail.content.replace(IMG, IMG_WIDTH_AUTO);
            webView.loadHtml(htmlData);
        }
    }

    public class JavascriptInterface {

        private Context context;


        public JavascriptInterface(Context context) {
            this.context = context;
        }

        @android.webkit.JavascriptInterface
        public void openImage(String img) {
            ImageDetailActivity.launch(context, img);
        }
    }
}
