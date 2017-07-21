package com.future.awaker.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.future.awaker.R;
import com.future.awaker.base.listener.DebouncingOnClickListener;
import com.future.awaker.data.BannerItem;
import com.future.awaker.imageloader.ImageLoader;
import com.future.awaker.news.NewDetailActivity;
import com.future.awaker.widget.banner.BannerImageView;
import com.future.awaker.widget.banner.RZLoopAdapter;
import com.future.awaker.widget.banner.RZLoopViewPager;

import java.util.List;

/**
 * Copyright ©2017 by Teambition
 */

public class HomeBannerAdapter extends RZLoopAdapter implements BannerImageView.OnBannerClickListener {

    private List<BannerItem> bannerItemList;

    public HomeBannerAdapter(int realCount, RZLoopViewPager viewPager, List<BannerItem> bannerItemList) {
        super(realCount, viewPager);
        this.bannerItemList = bannerItemList;
    }

    @Override
    public View instantiateItemView(ViewGroup container, int realPosition) {
        return LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_home_banner_item, container, false);
    }

    @Override
    public void initViewToData(View view, int realPosition) {
        BannerImageView bannerImageView =
                (BannerImageView) view.findViewById(R.id.banner_iv);
        BannerItem bannerItem = bannerItemList.get(realPosition);
        ImageLoader.get().load(bannerImageView, bannerItem.img_url);


        bannerImageView.setOnBannerClickListener(this);
        bannerImageView.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                NewDetailActivity.launch(v.getContext(), bannerItem.newsid,
                        bannerItem.title, bannerItem.img_url);
            }
        });
    }

    @Override
    public void actionDown() {
        mRZLoopViewPager.stopLoopPager();
    }

    @Override
    public void actionUp() {
        mRZLoopViewPager.startLoopPager();
    }
}
