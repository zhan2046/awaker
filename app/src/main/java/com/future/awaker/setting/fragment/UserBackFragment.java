package com.future.awaker.setting.fragment;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.future.awaker.Application;
import com.future.awaker.R;
import com.future.awaker.base.BaseFragment;
import com.future.awaker.base.listener.DebouncingOnClickListener;
import com.future.awaker.databinding.FragUserBackBinding;
import com.future.awaker.setting.viewmodel.UserBackViewModel;
import com.future.awaker.util.KeyboardUtils;

/**
 * Copyright ©2017 by ruzhan
 */

public class UserBackFragment extends BaseFragment<FragUserBackBinding> {

    private UserBackViewModel userBackViewModel;
    private SendCallBack sendCallBack = new SendCallBack();

    public static UserBackFragment newInstance() {
        return new UserBackFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_user_back;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.toolbar.setTitle(R.string.user_back);
        setToolbar(binding.toolbar);

        userBackViewModel = new UserBackViewModel();
        binding.setViewModel(userBackViewModel);
        addRunStatusChangeCallBack(userBackViewModel);

        userBackViewModel.isSend.addOnPropertyChangedCallback(sendCallBack);


        binding.contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String result = s.toString();
                binding.sendBtn.setEnabled(!TextUtils.isEmpty(result));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.sendBtn.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.send_email)
                        .content(R.string.send_email_content)
                        .positiveText(R.string.confirm)
                        .negativeText(R.string.cancel)
                        .theme(Theme.LIGHT)
                        .contentColorRes(R.color.themePrimaryDark)
                        .negativeColorRes(R.color.text_color)
                        .positiveColorRes(R.color.text_color)
                        .onPositive((dialog, which) -> userBackViewModel.sendEmail())
                        .onNegative((dialog, which) -> dialog.dismiss())
                        .show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        KeyboardUtils.closeSoftInput(getActivity(), binding.contentEt);
        userBackViewModel.isSend.removeOnPropertyChangedCallback(sendCallBack);
        userBackViewModel.clear();
        super.onDestroyView();
    }

    private class SendCallBack extends Observable.OnPropertyChangedCallback {

        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            if (userBackViewModel.isSend.get()) {
                Toast.makeText(Application.get(),
                        R.string.send_email_finish, Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }
    }
}
