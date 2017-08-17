package com.future.awaker.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.future.awaker.Account;
import com.future.awaker.Application;
import com.future.awaker.R;
import com.future.awaker.data.UserInfo;
import com.future.awaker.login.listener.LoginListener;

/**
 * Copyright ©2017 by Teambition
 */

public class LoginActivity extends AppCompatActivity implements LoginListener {

    public static final int RESULT_CODE_SUC = 10001;

    private LoginFragment loginFragment;

    public static void launch(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);

        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            loginFragment.setLoginListener(this);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_fl, loginFragment,
                        LoginFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onLoginSuc(UserInfo userInfo) {
        Account.get().setUserInfoToLocal(userInfo);
        String str = getResources().getString(R.string.welcome_back, userInfo.data_1.nickname);
        Toast.makeText(Application.get(), str, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        setResult(RESULT_CODE_SUC, intent);
        finish();
    }
}
