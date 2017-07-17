package com.future.awaker.video;


import android.view.View;

import com.future.awaker.R;
import com.future.awaker.base.BaseListFragment;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.Special;
import com.future.awaker.databinding.FragVideoBinding;

/**
 * Copyright ©2017 by Teambition
 */

public class VideoListFragment extends BaseListFragment<FragVideoBinding> implements OnItemClickListener<Special> {

    private VideoViewModel videoViewModel = new VideoViewModel();
    private VideoListAdapter adapter;

    public static VideoListFragment newInstance() {
        return new VideoListFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_video;
    }

    @Override
    protected void initData() {
        setListViewModel(videoViewModel);

        binding.setViewModel(videoViewModel);

        adapter = new VideoListAdapter(videoViewModel, this);
        binding.recyclerView.setAdapter(adapter);
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
}
