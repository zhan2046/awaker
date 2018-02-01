package com.future.awaker.data;

import android.arch.persistence.room.Embedded;

/**
 * Copyright ©2017 by ruzhan
 */

public class UserInfo {

    @Embedded(prefix = "user_info_data")
    public UserData data;
    @Embedded(prefix = "user_info_data1")
    public UserDetail data_1;
    public String info;
    public int code;
}
