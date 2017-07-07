package com.love.awaker.base.listener;

import android.view.View;

/**
 * Copyright ©2017 by Teambition
 */

public interface OnItemClickListener<T> {

    void onItemClick(View view, int position, T bean);
}
