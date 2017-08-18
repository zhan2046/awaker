package com.future.awaker.login.fragment;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.future.awaker.R;
import com.future.awaker.base.BaseFragment;
import com.future.awaker.base.listener.DebouncingOnClickListener;
import com.future.awaker.data.UserInfo;
import com.future.awaker.databinding.FragRegisterBinding;
import com.future.awaker.login.listener.LoginListener;
import com.future.awaker.login.viewmodel.RegisterViewModel;
import com.future.awaker.util.KeyboardUtils;

/**
 * Copyright ©2017 by ruzhan
 */

public class RegisterFragment extends BaseFragment<FragRegisterBinding> {

    private RegisterViewModel registerViewModel = new RegisterViewModel();
    private RegisterCallBack registerCallBack = new RegisterCallBack();

    private LoginListener listener;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    public void setLoginListener(LoginListener listener) {
        this.listener = listener;
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_register;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setViewModel(registerViewModel);
        addRunStatusChangeCallBack(registerViewModel);
        registerViewModel.userInfo.addOnPropertyChangedCallback(registerCallBack);

        binding.toolbar.setTitle(R.string.register);
        setToolbar(binding.toolbar);

        binding.registerBtn.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                registerViewModel.register();
            }
        });

        binding.pwd2Et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.pwdLayout.setErrorEnabled(false);

                String result = s.toString();
                binding.registerBtn.setEnabled(!TextUtils.isEmpty(result));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.pwd2Et.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_GO) {
                registerViewModel.register();
            }
            return false;
        });
    }

    @Override
    public void onDestroyView() {
        KeyboardUtils.closeSoftInput(getActivity(), binding.pwdEt);
        registerViewModel.userInfo.removeOnPropertyChangedCallback(registerCallBack);
        registerViewModel.clear();
        super.onDestroyView();
    }

    private class RegisterCallBack extends Observable.OnPropertyChangedCallback {

        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            UserInfo userInfo = registerViewModel.userInfo.get();
            if (userInfo != null && listener != null) {
                listener.onLoginSuc(userInfo);
            }
        }
    }
}
