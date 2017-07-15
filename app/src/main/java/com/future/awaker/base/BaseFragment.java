package com.future.awaker.base;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
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

    protected View progressBar;
    protected BaseViewModel baseViewModel;

    private RunningCallback runningCallback;
    private EmptyCallback emptyCallback = new EmptyCallback();

    protected abstract int getLayout();

    protected abstract void onCreateViewBind();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        onCreateViewBind();
        return binding.getRoot();
    }

    public void setViewModel(BaseViewModel viewModel) {
        if (viewModel == null) {
            return;
        }
        baseViewModel = viewModel;
        if (runningCallback == null) {
            runningCallback = new RunningCallback();
        }
        baseViewModel.isEmpty.addOnPropertyChangedCallback(emptyCallback);
        baseViewModel.isRunning.addOnPropertyChangedCallback(runningCallback);
    }

    @Override
    public void onDestroyView() {
        if (baseViewModel != null) {
            baseViewModel.isEmpty.removeOnPropertyChangedCallback(emptyCallback);

            baseViewModel.isRunning.removeOnPropertyChangedCallback(runningCallback);
        }

        super.onDestroyView();
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
                getActivity().finish();
            }
        });

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    public void showProgressBar() {
        if (progressBar != null && progressBar.getVisibility() != View.VISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        if (progressBar != null && progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.GONE);
        }
    }

    protected void onRunChanged(Observable sender, int propertyId) {
        // empty
    }

    private class RunningCallback extends Observable.OnPropertyChangedCallback {

        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            onRunChanged(sender, propertyId);

            ObservableBoolean isRunning = (ObservableBoolean) sender;
            if (isRunning.get()) {
                showProgressBar();

            } else {
                hideProgressBar();
            }
        }
    }

    private class EmptyCallback extends Observable.OnPropertyChangedCallback {

        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            onRunChanged(sender, propertyId);
            emptyData(baseViewModel.isEmpty.get());
        }
    }

    protected void emptyData(boolean isEmpty) {

    }
}

