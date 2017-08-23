package com.future.awaker.video.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.future.awaker.R;
import com.future.awaker.base.BaseFragment;
import com.future.awaker.databinding.FragVideoDetailBinding;
import com.future.awaker.util.HtmlParser;
import com.future.awaker.util.UiUtils;
import com.just.library.AgentWeb;
import com.just.library.AgentWebSettings;
import com.just.library.WebDefaultSettingsManager;

/**
 * Created by ruzhan on 2017/7/16.
 */

public class VideoDetailFragment extends BaseFragment<FragVideoDetailBinding> {

    private static final String VIDEO_HTML = "videoHtml";
    private static final String IFRAME = "<iframe";
    private static final String IFRAME_AUTO = "<iframe style='max-width:100%;height:350;'";

    protected AgentWeb mAgentWeb;
    private WebView webView;

    public static VideoDetailFragment newInstance(String videoHtml) {
        Bundle args = new Bundle();
        args.putString(VIDEO_HTML, videoHtml);
        VideoDetailFragment fragment = new VideoDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_video_detail;
    }

    @Override
    protected void onCreateBindView() {
        initWebView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String videoHtml = getArguments().getString(VIDEO_HTML, "");

        int height = UiUtils.getScreenHeight();
        int width = UiUtils.getScreenWidth();

        String htmlData = videoHtml.replace(IFRAME, IFRAME_AUTO);
        String html = HtmlParser.loadDataWith(htmlData);
        webView.loadData(html, "text/html; charset=UTF-8", null);
    }

    private void initWebView() {
        FrameLayout.LayoutParams lp =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);

        webView = new WebView(getContext());
        webView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black));

        mAgentWeb = AgentWeb.with(this)//
                .setAgentWebParent(binding.contentFl, lp)
                .closeDefaultIndicator()//
                .setWebView(webView)
                .setAgentWebWebSettings(getSettings())//
                .setWebViewClient(new WebViewClient())
                .setWebChromeClient(new WebChromeClient())
                .setReceivedTitleCallback((view1, title) -> {

                })
                .setSecurityType(AgentWeb.SecurityType.strict)
                .createAgentWeb()//
                .ready()//
                .go(null);

        setDesktopMode(webView, false);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.setKeepScreenOn(true);
    }

    public void setDesktopMode(WebView webView, final boolean enabled) {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAgentWeb.uploadFileResult(requestCode, resultCode, data);
    }

    public AgentWebSettings getSettings() {
        return WebDefaultSettingsManager.getInstance();
    }

    @SuppressLint("NewApi")
    @Override
    public void onResume() {
        //mAgentWeb.getWebLifeCycle().onResume();
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

        super.onDestroy();
    }
}
