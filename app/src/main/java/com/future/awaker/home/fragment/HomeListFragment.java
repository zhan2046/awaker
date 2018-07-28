package com.future.awaker.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.future.awaker.R;
import com.future.awaker.base.BaseFragment;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.HomeItem;
import com.future.awaker.databinding.FragHomeListBinding;
import com.future.awaker.home.adapter.HomeListAdapter;
import com.future.awaker.home.viewmodel.HomeViewModel;
import com.future.awaker.news.activity.HomeTypeListActivity;

/**
 * Copyright ©2017 by ruzhan
 */

public class HomeListFragment extends BaseFragment<FragHomeListBinding>
        implements OnItemClickListener<HomeItem> {

    private static final int SPAN_COUNT = 3;

    private HomeViewModel homeViewModel;

    public static HomeListFragment newInstance() {
        return new HomeListFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_home_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel = new HomeViewModel();

        HomeListAdapter adapter = new HomeListAdapter(this);
        binding.recyclerView.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getSpanSize(position);
            }
        });
        binding.recyclerView.setLayoutManager(manager);
        adapter.setData();

        homeViewModel.getBannerLiveData().observe(this, adapter::setData);

        homeViewModel.initLocalBanners();
        homeViewModel.getBanner();
    }

    @Override
    public void onDestroy() {
        homeViewModel.clear();
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, int position, HomeItem bean) {
        HomeTypeListActivity.launch(getActivity(), bean.id, bean.title);
    }
}
