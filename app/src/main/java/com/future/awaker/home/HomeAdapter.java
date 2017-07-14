package com.future.awaker.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.future.awaker.data.Video;
import com.future.awaker.news.NewListFragment;
import com.future.awaker.news.NewViewModel;
import com.future.awaker.video.VideoListFragment;
import com.future.awaker.video.VideoViewModel;

import java.util.List;

/**
 * Copyright ©2017 by Teambition
 */

public class HomeAdapter extends FragmentPagerAdapter {

    private List<String> titles;
    private NewViewModel newViewModel;
    private VideoViewModel videoViewModel;

    public HomeAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        this.titles = titles;
    }

    public void setNewViewModel(NewViewModel viewModel) {
        newViewModel = viewModel;
    }

    public void setVideoViewModel(VideoViewModel viewModel) {
        videoViewModel = viewModel;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                NewListFragment newListFragment = NewListFragment.newInstance();
                newListFragment.setNewViewModel(newViewModel);
                fragment = newListFragment;
                break;
            case 1:
                VideoListFragment videoListFragment = VideoListFragment.newInstance();
                videoListFragment.setVideoViewModel(videoViewModel);
                fragment = videoListFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return titles == null ? 0 : titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
