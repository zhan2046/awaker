package com.future.awaker.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.future.awaker.base.listener.DebouncingOnClickListener;

/**
 * Copyright ©2017 by Teambition
 */

public abstract class BaseFragment<VB extends ViewDataBinding> extends Fragment {

    protected VB binding;

    @SuppressWarnings("unchecked")
    protected <T> T findViewById(View view, int id) {
        return (T) view.findViewById(id);
    }

    protected abstract int getLayout();

    protected void onCreateBindView() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        onCreateBindView();
        return binding.getRoot();
    }

    protected void setToolbar(Toolbar toolbar) {
        if (toolbar == null) {
            return;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                getActivity().onBackPressed();
            }
        });

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}

