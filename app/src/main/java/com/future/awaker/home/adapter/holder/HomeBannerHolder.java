package com.future.awaker.home.adapter.holder;

import android.support.v7.widget.RecyclerView;

import com.future.awaker.data.Banner;
import com.future.awaker.databinding.ItemHomeBannerBinding;
import com.future.awaker.home.adapter.HomeBannerAdapter;

/**
 * Copyright ©2017 by ruzhan
 */

public class HomeBannerHolder extends RecyclerView.ViewHolder {

    private ItemHomeBannerBinding binding;
    private HomeBannerAdapter adapter;

    public HomeBannerHolder(ItemHomeBannerBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        adapter = new HomeBannerAdapter();
        binding.viewPager.setAdapter(adapter);
    }

    public void bind(Banner bean) {
        adapter.setData(bean.list);
    }
}
