package com.future.awaker.home.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.future.awaker.R;
import com.future.awaker.base.listener.DebouncingOnClickListener;
import com.future.awaker.data.BannerItem;
import com.future.awaker.imageloader.ImageLoader;
import com.future.awaker.news.NewDetailActivity;

import java.util.List;

/**
 * Copyright ©2017 by ruzhan
 */

public class HomeBannerAdapter extends PagerAdapter {

    private SparseArray<View> mViews = new SparseArray<>();
    private List<BannerItem> bannerItemList;

    public void setData(List<BannerItem> bannerItemList) {
        this.bannerItemList = bannerItemList;
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        if (view == null) {
            view = LayoutInflater.from(container.getContext())
                    .inflate(R.layout.item_home_banner_item, container, false);
            mViews.put(position, view);
        }
        initViewToData(view, position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void initViewToData(View view, int position) {
        ImageView imageView =
                (ImageView) view.findViewById(R.id.banner_iv);
        BannerItem bannerItem = bannerItemList.get(position);
        ImageLoader.get().load(imageView, bannerItem.img_url);

        imageView.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                NewDetailActivity.launch(v.getContext(), bannerItem.newsid,
                        bannerItem.title, bannerItem.img_url);
            }
        });
    }

    @Override
    public int getCount() {
        return bannerItemList == null ? 0 : bannerItemList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
