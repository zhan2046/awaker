package com.future.awaker.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.future.awaker.news.NewFragment;
import com.future.awaker.news.NewViewModel;

import java.util.List;

/**
 * Copyright ©2017 by Teambition
 */

public class HomeAdapter extends FragmentPagerAdapter {

    private List<String> titles;
    private NewViewModel newViewModel;

    public HomeAdapter(FragmentManager fm, List<String> titles) {
        super(fm);
        this.titles = titles;
    }

    public void setViewModel(NewViewModel viewModel) {
        newViewModel = viewModel;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                NewFragment newFragment = NewFragment.newInstance();
                newFragment.setNewViewModel(newViewModel);
                fragment = newFragment;
                break;
            case 1:
                NewFragment newFragment2 = NewFragment.newInstance();
                newFragment2.setNewViewModel(newViewModel);
                fragment = newFragment2;
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
