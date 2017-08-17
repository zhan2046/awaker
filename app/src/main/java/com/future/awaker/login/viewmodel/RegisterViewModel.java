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

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Copyright ©2017 by Teambition
 */

public class RegisterViewModel extends BaseViewModel {

    private static final String TAG = RegisterViewModel.class.getSimpleName();

    public ObservableField<String> emailStr = new ObservableField<>();
    public ObservableField<String> nikeStr = new ObservableField<>();
    public ObservableField<String> pwdStr = new ObservableField<>();
    public ObservableField<String> pwd2Str = new ObservableField<>();

    public ObservableField<UserInfo> userInfo = new ObservableField<>();

    public void register() {
        if (isRunning.get()) {
            return;
        }
        String email = emailStr.get();
        String nike = nikeStr.get();
        String pwd = pwdStr.get();
        String pwd2 = pwd2Str.get();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(nike)
                || TextUtils.isEmpty(pwd)
                || TextUtils.isEmpty(pwd2)) {
            return;
        }

        if (!Objects.equals(pwd, pwd2)) {
            Toast.makeText(Application.get(), R.string.pwd_check, Toast.LENGTH_SHORT).show();
            return;
        }

        disposable.add(AwakerRepository.get().register(TOKEN, email, nike, pwd)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    LogUtils.showLog(TAG, throwable.toString());
                    Toast.makeText(Application.get(), R.string.register_error, Toast.LENGTH_SHORT).show();
                })
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> {
                    if (!TextUtils.isEmpty(result.getInfo())) {
                        Toast.makeText(Application.get(), "" +
                                result.getInfo(), Toast.LENGTH_SHORT).show();
                    }
                    loginToAccount();
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void loginToAccount() {
        String account = emailStr.get();
        String pwd = pwdStr.get();
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
                        Toast.makeText(Application.get(), "" +
                                result.info, Toast.LENGTH_SHORT).show();
                    }
                    userInfo.set(result);
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }
}
