package com.future.awaker.home;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.future.awaker.R;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.Banner;
import com.future.awaker.data.BannerItem;
import com.future.awaker.data.HomeItem;
import com.future.awaker.databinding.ItemHomeBannerBinding;
import com.future.awaker.databinding.ItemHomeListBinding;
import com.future.awaker.home.holder.HomeBannerHolder;
import com.future.awaker.home.holder.HomeListHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright ©2017 by Teambition
 */

public class HomeListAdapter extends RecyclerView.Adapter {

    private static final int TYPE_BANNER = 1000;
    private static final int TYPE_ITEM = 1001;

    private List<Object> dataList = new ArrayList<>();
    private OnItemClickListener<HomeItem> listener;

    public HomeListAdapter(OnItemClickListener<HomeItem> listener) {
        this.listener = listener;
    }

    public void setBanner(Banner banner) {
        if (banner == null) {
            return;
        }
        dataList.clear();

        dataList.add(banner);
        dataList.addAll(HomeItem.getList());
        notifyDataSetChanged();
    }

    public void setData() {
        dataList.clear();

        dataList.addAll(HomeItem.getList());
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = TYPE_ITEM;
        Object object = dataList.get(position);
        if (object instanceof Banner) {
            viewType = TYPE_BANNER;
        }
        return viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == TYPE_BANNER) {
            ItemHomeBannerBinding homeBannerBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_home_banner, parent, false);
            HomeBannerHolder bannerHolder = new HomeBannerHolder(homeBannerBinding);
            viewHolder = bannerHolder;

        } else if (viewType == TYPE_ITEM) {
            ItemHomeListBinding homeListBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.item_home_list, parent, false);
            HomeListHolder homeListHolder = new HomeListHolder(homeListBinding, listener);
            viewHolder = homeListHolder;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_BANNER) {
            ((HomeBannerHolder)holder).bind((Banner) dataList.get(position));
        } else if (viewType == TYPE_ITEM) {
            ((HomeListHolder)holder).bind((HomeItem) dataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public int getSpanSize(int position) {
        int spanSize = 1;
        int viewType = getItemViewType(position);
        if (viewType == TYPE_BANNER) {
            spanSize = 3;
        }
        return spanSize;
    }
}
