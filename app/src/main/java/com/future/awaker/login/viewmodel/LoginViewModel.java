package com.future.awaker.login.viewmodel;

import android.databinding.ObservableField;
import android.text.TextUtils;
import android.widget.Toast;

import com.future.awaker.Application;
import com.future.awaker.R;
import com.future.awaker.base.viewmodel.BaseViewModel;
import com.future.awaker.data.UserInfo;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.source.AwakerRepository;
import com.future.awaker.util.LogUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Copyright ©2017 by Teambition
 */

public class LoginViewModel extends BaseViewModel {

    private static final String TAG = LoginViewModel.class.getSimpleName();

    public ObservableField<String> loginAccountStr = new ObservableField<>();
    public ObservableField<String> loginPwdStr = new ObservableField<>();

    public ObservableField<UserInfo> userInfo = new ObservableField<>();

    public void loginToAccount() {
        if (isRunning.get()) {
            return;
        }
        String account = loginAccountStr.get();
        String pwd = loginPwdStr.get();
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
            return;
        }

        disposable.add(AwakerRepository.get().login(TOKEN, account, pwd)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    LogUtils.showLog(TAG, throwable.toString());
                    Toast.makeText(Application.get(), R.string.login_error, Toast.LENGTH_SHORT).show();
                })
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    if (!TextUtils.isEmpty(result.info)) {
                        Toast.makeText(Application.get(), "" + result.info, Toast.LENGTH_SHORT).show();

                    }
                    userInfo.set(result);
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }
}
