package com.future.awaker.video;


import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.future.awaker.R;
import com.future.awaker.base.BaseListFragment;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.Video;
import com.future.awaker.databinding.FragVideoBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright ©2017 by Teambition
 */

public class VideoListFragment extends BaseListFragment<FragVideoBinding> implements OnItemClickListener<Video> {

    private VideoViewModel videoViewModel;

    public static VideoListFragment newInstance() {
        return new VideoListFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_video;
    }

    @Override
    protected int getEmptyLayout() {
        return R.layout.layout_empty;
    }

    @Override
    protected void initData() {
        setViewModel(videoViewModel);

        binding.setViewModel(videoViewModel);

        VideoListAdapter adapter = new VideoListAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        videoViewModel.setToken("f32b30c2a289bfca2c9857ffc5871ac8", 0);
    }

    public void setVideoViewModel(VideoViewModel videoViewModel) {
        this.videoViewModel = videoViewModel;
    }

    @BindingAdapter({"videos"})
    public static void setVideos(RecyclerView recyclerView, List<Video> videos) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof VideoListAdapter) {
            ((VideoListAdapter) adapter).setData(new ArrayList<>(videos));
        }
    }

    @Override
    public void onItemClick(View view, int position, Video bean) {

    }
}
