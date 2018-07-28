package com.future.awaker.video.fragment;


import android.view.View;

import com.future.awaker.R;
import com.future.awaker.base.BaseListFragment;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.Special;
import com.future.awaker.databinding.FragVideoBinding;
import com.future.awaker.video.SpecialListActivity;
import com.future.awaker.video.adapter.VideoListAdapter;
import com.future.awaker.video.viewmodel.VideoViewModel;

/**
 * Copyright ©2017 by ruzhan
 */

public class VideoListFragment extends BaseListFragment<FragVideoBinding>
        implements OnItemClickListener<Special> {

    private static final String TAG = "VideoListFragment";

    private VideoViewModel videoViewModel;

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
        binding.setViewModel(videoViewModel);
        setListViewModel(videoViewModel);

        VideoListAdapter adapter = new VideoListAdapter(videoViewModel, this);
        binding.recyclerView.setAdapter(adapter);

        videoViewModel.getSpecialLiveData().observe(this, refreshListModel -> {
            if (refreshListModel != null) {
                if (refreshListModel.isRefreshType()) {
                    adapter.setRefreshData(refreshListModel.list);

                } else if (refreshListModel.isUpdateType()) {
                    adapter.setUpdateData(refreshListModel.list);
                }
            }
        });

        videoViewModel.loadSpecialListEntity(String.valueOf(Special.NORMAL));

        onRefresh();
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
}
