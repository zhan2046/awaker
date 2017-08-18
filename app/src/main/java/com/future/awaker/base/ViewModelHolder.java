package com.future.awaker.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Copyright ©2017 by ruzhan
 */

public class ViewModelHolder<VM> extends Fragment {

    private VM viewModel;

    public ViewModelHolder() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static <M> ViewModelHolder createContainer(M viewModel) {
        ViewModelHolder<M> viewModelContainer = new ViewModelHolder<>();
        viewModelContainer.setViewModel(viewModel);
        return viewModelContainer;
    }

    public void setViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }

    public VM getViewModel() {
        return viewModel;
    }
}
