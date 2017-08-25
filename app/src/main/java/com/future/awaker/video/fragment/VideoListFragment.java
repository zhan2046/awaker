package com.future.awaker.video.fragment;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.future.awaker.R;
import com.future.awaker.base.BaseListFragment;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.base.listener.onPageSelectedListener;
import com.future.awaker.data.Special;
import com.future.awaker.databinding.FragVideoBinding;
import com.future.awaker.home.HomeActivity;
import com.future.awaker.util.LogUtils;
import com.future.awaker.video.SpecialListActivity;
import com.future.awaker.video.adapter.VideoListAdapter;
import com.future.awaker.video.viewmodel.VideoViewModel;

/**
 * Copyright ©2017 by ruzhan
 */

public class VideoListFragment extends BaseListFragment<FragVideoBinding>
        implements OnItemClickListener<Special>, onPageSelectedListener {

    private static final String TAG = VideoListFragment.class.getSimpleName();

    private static final int SCROLL_OFFSET_TIME = 350;
    private static final int MIN_OFFSET = 30;

    private VideoViewModel videoViewModel;
    private VideoListAdapter adapter;
    private boolean isFirst;

    private long offsetTime;
    private boolean isScrolledDown;
    private boolean onScrolledUp;

    public static VideoListFragment newInstance() {
        return new VideoListFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_video;
    }

    @Override
    protected void initData() {
        videoViewModel = new VideoViewModel();
        setListViewModel(videoViewModel);

        binding.setViewModel(videoViewModel);

        adapter = new VideoListAdapter(videoViewModel, this);
        binding.recyclerView.setAdapter(adapter);

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                updateFab(dy);
            }
        });
    }

    private void updateFab(int dy) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - offsetTime < SCROLL_OFFSET_TIME) {
            return;
        }
        offsetTime = currentTime;

        if (dy > MIN_OFFSET && !isScrolledDown) {
            isScrolledDown = true;

            onScrolledUp = false;
            LogUtils.showLog(TAG, "updateFab onScrolledDown");
            onScrolledDown();

        } else if (dy < -MIN_OFFSET && !onScrolledUp) {
            onScrolledUp = true;

            isScrolledDown = false;
            LogUtils.showLog(TAG, "updateFab onScrolledUp");
            onScrolledUp();
        }
    }

    private void onScrolledDown() {
        HomeActivity activity = (HomeActivity) getActivity();
        activity.showFab(false);
    }

    private void onScrolledUp() {
        HomeActivity activity = (HomeActivity) getActivity();
        activity.showFab(true);
    }

    @Override
    public void onDestroyView() {
        videoViewModel.clear();
        super.onDestroyView();
    }

    @Override
    public void onItemClick(View view, int position, Special bean) {
        SpecialListActivity.launch(getActivity(), bean.id, bean.title, bean.cover);
    }

    public void setCat(int cat) {
        videoViewModel.setCat(cat);
        onRefresh();
    }

    @Override
    public void onPageSelected(int position) {
        LogUtils.d("VideoListFragment onPageSelected");
        if (!isFirst) {
            onRefresh();
            isFirst = true;
        }
    }
}
