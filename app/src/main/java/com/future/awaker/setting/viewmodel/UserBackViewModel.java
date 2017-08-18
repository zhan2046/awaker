package com.future.awaker.setting.viewmodel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.text.TextUtils;

import com.future.awaker.base.viewmodel.BaseViewModel;
import com.future.awaker.email.SendMailUtil;

/**
 * Copyright ©2017 by Teambition
 */

public class UserBackViewModel extends BaseViewModel {

    public ObservableField<String> contentStr = new ObservableField<>();
    public ObservableBoolean isSend = new ObservableBoolean(false);

    public void sendEmail() {
        String content = contentStr.get();
        if (TextUtils.isEmpty(content)) {
            return;
        }
        SendMailUtil.send(content);
        isSend.set(true);
    }
}
