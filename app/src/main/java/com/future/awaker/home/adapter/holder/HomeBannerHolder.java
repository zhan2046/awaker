package com.future.awaker.home.adapter.holder;

import android.support.v7.widget.RecyclerView;

import com.future.awaker.data.Banner;
import com.future.awaker.data.BannerItem;
import com.future.awaker.databinding.ItemHomeBannerBinding;
import com.future.awaker.home.adapter.HomeBannerAdapter;

import java.util.List;

/**
 * Copyright ©2017 by ruzhan
 */

public class HomeBannerHolder extends RecyclerView.ViewHolder {

    private ItemHomeBannerBinding binding;
    private HomeBannerAdapter adapter;

    public HomeBannerHolder(ItemHomeBannerBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Banner bean) {
        initBanner(bean);
    }

    private void initBanner(Banner banner) {
        List<BannerItem> list = banner.list;
        if (adapter != null || list == null || list.isEmpty()) {
            return;
        }
        adapter = new HomeBannerAdapter(list.size(), binding.viewPager, list);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.startLoopPager();
    }
}
