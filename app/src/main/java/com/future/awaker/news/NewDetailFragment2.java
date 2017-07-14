package com.future.awaker.news;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.Observable;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.future.awaker.Application;
import com.future.awaker.R;
import com.future.awaker.base.BaseFragment;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.source.NewRepository;
import com.future.awaker.databinding.FragNewDetail2Binding;
import com.future.awaker.imageloader.ImageLoader;
import com.just.library.AgentWeb;
import com.just.library.AgentWebSettings;
import com.just.library.WebDefaultSettingsManager;

import im.delight.android.webview.AdvancedWebView;

/**
 * Copyright ©2017 by Teambition
 */

public class NewDetailFragment2 extends BaseFragment<FragNewDetail2Binding> implements SwipeRefreshLayout.OnRefreshListener {

    private static final String IMG = "<img";
    private static final String IMG_WIDTH_AUTO = "<img style='max-width:100%;height:auto;'";

    private static final String IFRAME = "<iframe";
    private static final String IFRAME_AUTO = "<iframe style='max-width:100%;height:200;'";
    private static final String NEW_ID = "newId";

    private NewDetailViewModel viewModel;
    private NewDetailBack newDetailBack = new NewDetailBack();
    protected AgentWeb mAgentWeb;
    private AdvancedWebView webView;

    public static NewDetailFragment2 newInstance(String newId) {
        Bundle args = new Bundle();
        args.putString(NEW_ID, newId);
        NewDetailFragment2 fragment = new NewDetailFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_new_detail2;
    }

    @Override
    protected void onCreateViewBind() {
        binding.swipeRefresh.setOnRefreshListener(this);
        binding.swipeRefresh.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String newId = getArguments().getString(NEW_ID);
        viewModel = new NewDetailViewModel(NewRepository.get());
        viewModel.setNewId(newId);

        setViewModel(viewModel);
        viewModel.newDetail.addOnPropertyChangedCallback(newDetailBack);

        FrameLayout.LayoutParams lp =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        webView = new AdvancedWebView(getContext());

        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(binding.containerFl, lp)//
                .closeDefaultIndicator()//
                .setWebView(webView)
                .setWebSettings(getSettings())//
                .setWebViewClient(new WebViewClient())
                .setWebChromeClient(new WebChromeClient())
                .setReceivedTitleCallback((view1, title) -> {

                })
                .setSecurityType(AgentWeb.SecurityType.strict)
                .createAgentWeb()//
                .ready()//
                .go(null);

        webView.setDesktopMode(false);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.addJavascriptInterface(new JavascriptInterface(getActivity()), "imagelistner");
        webView.setListener(getActivity(), new AdvancedWebView.Listener() {
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

        onRefresh();
    }

    public AgentWebSettings getSettings() {
        return WebDefaultSettingsManager.getInstance();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAgentWeb.uploadFileResult(requestCode, resultCode, data);
    }

    protected void setRefreshing(boolean refresh) {
        if (binding.swipeRefresh != null) {
            binding.swipeRefresh.post(() -> {
                if (binding.swipeRefresh != null) {
                    binding.swipeRefresh.setRefreshing(refresh);
                }
            });
        }
    }

    @Override
    protected void onRunChanged(Observable sender, int propertyId) {
        super.onRunChanged(sender, propertyId);
        if (viewModel != null && !viewModel.isRunning.get()) {
            setRefreshing(false);
        }
    }

    @Override
    public void onRefresh() {
        viewModel.fetchData(true, 1);
    }

    private class NewDetailBack extends Observable.OnPropertyChangedCallback {

        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            NewDetail newDetail = viewModel.newDetail.get();
            binding.userTv.setText(newDetail.user == null ? "" : newDetail.user.nickname);
            String userUrl = newDetail.user == null ? "" : newDetail.user.avatar128;
            ImageLoader.get().loadThumb(binding.userIv, userUrl);

            String headerUrl = newDetail.cover_url == null ? "" : newDetail.cover_url.ori;
            ImageLoader.get().loadThumb(binding.iconIv, headerUrl);

            String htmlData = newDetail.content.replace(IMG, IMG_WIDTH_AUTO);
            htmlData = htmlData.replace(IFRAME, IFRAME_AUTO);
            webView.loadHtml(htmlData);        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @SuppressLint("NewApi")
    @Override
    public void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @SuppressLint("NewApi")
    @Override
    public void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        viewModel.newDetail.removeOnPropertyChangedCallback(newDetailBack);
        super.onDestroy();
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
