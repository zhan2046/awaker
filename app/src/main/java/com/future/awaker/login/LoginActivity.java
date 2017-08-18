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
import com.future.awaker.login.fragment.LoginFragment;
import com.future.awaker.login.fragment.RegisterFragment;
import com.future.awaker.login.listener.LoginListener;

/**
 * Copyright ©2017 by Teambition
 */

public class LoginActivity extends AppCompatActivity implements LoginListener {

    private static final String TYPE_FLAG = "typeFlag";

    public static final int RESULT_CODE_SUC = 10001;

    public static final int LOGIN_FLAG = 3000;
    public static final int REGISTER_FLAG = 3001;

    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;

    public static void launch(Activity activity, int requestCode, int typeFlag) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra(TYPE_FLAG, typeFlag);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);

        int typeFlag = getIntent().getIntExtra(TYPE_FLAG, REGISTER_FLAG);
        if (typeFlag == LOGIN_FLAG) {
            switchLoginFragment();

        } else if (typeFlag == REGISTER_FLAG) {
            switchRegisterFragment();

        } else {
            switchRegisterFragment();
        }
    }

    private void switchLoginFragment() {
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

    private void switchRegisterFragment() {
        if (registerFragment == null) {
            registerFragment = RegisterFragment.newInstance();
            registerFragment.setLoginListener(this);
        }
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_fl, registerFragment,
                        RegisterFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void onLoginSuc(UserInfo userInfo) {
        Account.get().setUserInfoToLocal(userInfo);
        Account.get().setOpenIdToLocal(userInfo.data == null ? "" : userInfo.data.open_id);

        String str = getResources().getString(R.string.welcome_back, userInfo.data_1.nickname);
        Toast.makeText(Application.get(), str, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        setResult(RESULT_CODE_SUC, intent);
        finish();
    }
}
