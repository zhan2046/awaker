package com.future.awaker.setting.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.blankj.utilcode.util.AppUtils;
import com.future.awaker.R;
import com.future.awaker.base.BaseFragment;
import com.future.awaker.databinding.FragDevelopDescBinding;

/**
 * Copyright ©2017 by ruzhan
 */

public class DevelopDescFragment extends BaseFragment<FragDevelopDescBinding> {

    public static DevelopDescFragment newInstance() {
        return new DevelopDescFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_develop_desc;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.toolbar.setTitle(R.string.develop_desc);
        setToolbar(binding.toolbar);

        String versionName = AppUtils.getAppVersionName();
        int versionCode = AppUtils.getAppVersionCode();

        String versionStr = versionName + "   build(" + versionCode + ")";
        binding.versionTv.setText(versionStr);
    }
}
