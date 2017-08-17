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
import com.future.awaker.databinding.FragLoginBinding;
import com.future.awaker.login.listener.LoginListener;
import com.future.awaker.login.viewmodel.LoginViewModel;
import com.future.awaker.util.KeyboardUtils;

/**
 * Copyright ©2017 by Teambition
 */

public class LoginFragment extends BaseFragment<FragLoginBinding> {

    private LoginListener listener;
    private LoginViewModel loginViewModel;
    private LoginCallBack loginCallBack = new LoginCallBack();

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_login;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginViewModel = new LoginViewModel();
        binding.setViewModel(loginViewModel);
        addRunStatusChangeCallBack(loginViewModel);

        binding.toolbar.setTitle(R.string.login);
        setToolbar(binding.toolbar);

        loginViewModel.userInfo.addOnPropertyChangedCallback(loginCallBack);

        binding.loginBtn.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View v) {
                loginViewModel.loginToAccount();
            }
        });

        binding.pwdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.pwdLayout.setErrorEnabled(false);

                String result = s.toString();
                binding.loginBtn.setEnabled(!TextUtils.isEmpty(result));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.pwdEt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_GO) {
                loginViewModel.loginToAccount();
            }
            return false;
        });
    }

    @Override
    public void onDestroyView() {
        KeyboardUtils.closeSoftInput(getActivity(), binding.pwdEt);
        loginViewModel.userInfo.removeOnPropertyChangedCallback(loginCallBack);
        loginViewModel.clear();
        super.onDestroyView();
    }

    public void setLoginListener(LoginListener listener) {
        this.listener = listener;
    }

    private class LoginCallBack extends Observable.OnPropertyChangedCallback {

        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            UserInfo userInfo = loginViewModel.userInfo.get();
            if (listener != null) {
                listener.onLoginSuc(userInfo);
            }
        }
    }
}
