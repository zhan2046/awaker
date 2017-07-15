package com.future.awaker.video;


import android.databinding.ObservableList;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.future.awaker.R;
import com.future.awaker.base.BaseListFragment;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.Special;
import com.future.awaker.databinding.FragVideoBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright ©2017 by Teambition
 */

public class VideoListFragment extends BaseListFragment<FragVideoBinding> implements OnItemClickListener<Special> {

    private VideoViewModel videoViewModel;
    private VideoListAdapter adapter;

    private VideoListBack videoListBack = new VideoListBack();

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

        adapter = new VideoListAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        videoViewModel.specials.addOnListChangedCallback(videoListBack);
    }

    @Override
    public void onDestroyView() {
        videoViewModel.specials.removeOnListChangedCallback(videoListBack);
        super.onDestroyView();
    }

    @Override
    protected void emptyData(boolean isEmpty) {
        adapter.setEmpty(isEmpty);
    }

    public void setVideoViewModel(VideoViewModel videoViewModel) {
        this.videoViewModel = videoViewModel;
    }

    @Override
    public void onItemClick(View view, int position, Special bean) {

    }

    public void setCat(int cat) {
        videoViewModel.setCat(cat);
        onRefresh();
    }

    public void setData(List<Special> specials) {
        if (specials == null) {
            return;
        }
        adapter.setData(new ArrayList<>(specials));
        if (isRefresh) {
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            manager.scrollToPosition(0);
        }
    }

    private class VideoListBack extends ObservableList.OnListChangedCallback<ObservableList<Special>> {

        @Override
        public void onChanged(ObservableList<Special> sender) {

        }

        @Override
        public void onItemRangeChanged(ObservableList<Special> sender, int positionStart, int itemCount) {
            setData(sender);
        }

        @Override
        public void onItemRangeInserted(ObservableList<Special> sender, int positionStart, int itemCount) {
            setData(sender);
        }

        @Override
        public void onItemRangeMoved(ObservableList<Special> sender, int fromPosition, int toPosition, int itemCount) {
            setData(sender);
        }

        @Override
        public void onItemRangeRemoved(ObservableList<Special> sender, int positionStart, int itemCount) {
            setData(sender);
        }
    }
}
