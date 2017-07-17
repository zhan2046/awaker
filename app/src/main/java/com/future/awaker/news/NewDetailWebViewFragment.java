package com.future.awaker.news;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.future.awaker.R;
import com.future.awaker.base.BaseFragment;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.NewEle;
import com.future.awaker.databinding.FragNewDetail2Binding;
import com.future.awaker.util.HtmlParser;
import com.just.library.AgentWeb;
import com.just.library.AgentWebSettings;
import com.just.library.WebDefaultSettingsManager;

import java.util.List;

import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright ©2017 by Teambition
 */

public class NewDetailWebViewFragment extends BaseFragment<FragNewDetail2Binding> implements SwipeRefreshLayout.OnRefreshListener {

    private static final String IMG = "<img";
    private static final String IMG_WIDTH_AUTO = "<img style='max-width:100%;height:auto;'";

    private static final String IFRAME = "<iframe";
    private static final String IFRAME_AUTO = "<iframe style='max-width:100%;height:200;'";

    private static final String NEW_ID = "newId";
    private static final String NEW_TITLE = "newTitle";

    private NewDetailViewModel viewModel = new NewDetailViewModel();
    private NewDetailBack newDetailBack = new NewDetailBack();
    protected AgentWeb mAgentWeb;
    private WebView webView;

    public static NewDetailWebViewFragment newInstance(String newId, String newTitle) {
        Bundle args = new Bundle();
        args.putString(NEW_ID, newId);
        args.putString(NEW_TITLE, newTitle);
        NewDetailWebViewFragment fragment = new NewDetailWebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_new_detail2;
    }

    @Override
    protected void onCreateBindView() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String newId = getArguments().getString(NEW_ID);
        String newTitle = getArguments().getString(NEW_TITLE);

        viewModel.setNewId(newId);

        binding.toolbar.setTitle(newTitle);
        setToolbar(binding.toolbar);

        viewModel.newDetail.addOnPropertyChangedCallback(newDetailBack);

        initWebView();

        onRefresh();
    }

    private void initWebView() {
        FrameLayout.LayoutParams lp =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

        webView = new WebView(getContext());

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

        setDesktopMode(false);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    public void setDesktopMode(final boolean enabled) {
        WebSettings webSettings = webView.getSettings();

        final String newUserAgent;
        if (enabled) {
            newUserAgent = webSettings.getUserAgentString().replace("Mobile", "eliboM").replace("Android", "diordnA");
        } else {
            newUserAgent = webSettings.getUserAgentString().replace("eliboM", "Mobile").replace("diordnA", "Android");
        }

        webSettings.setUserAgentString(newUserAgent);
        webSettings.setUseWideViewPort(enabled);
        webSettings.setLoadWithOverviewMode(enabled);
        webSettings.setSupportZoom(enabled);
        webSettings.setBuiltInZoomControls(enabled);
    }


    public AgentWebSettings getSettings() {
        return WebDefaultSettingsManager.getInstance();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAgentWeb.uploadFileResult(requestCode, resultCode, data);
    }


    @Override
    public void onRefresh() {
        viewModel.fetchData(true);
    }

    private class NewDetailBack extends Observable.OnPropertyChangedCallback {

        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            NewDetail newDetail = viewModel.newDetail.get();

            String htmlData = newDetail.content.replace(IMG, IMG_WIDTH_AUTO);
            htmlData = htmlData.replace(IFRAME, IFRAME_AUTO);
            String html = HtmlParser.loadDataWithVideo(htmlData);
            webView.loadData(html, "text/html; charset=UTF-8", null);
        }
    }

    private void test(String html) {
        io.reactivex.Observable.create((ObservableOnSubscribe<List<NewEle>>) e -> e.onNext(HtmlParser.htmlToList(html)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::initNewEleList);
    }

    private void initNewEleList(List<NewEle> newEleList) {
        if (newEleList == null) {
            return;
        }

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
        viewModel.clear();
        super.onDestroy();
    }

}
