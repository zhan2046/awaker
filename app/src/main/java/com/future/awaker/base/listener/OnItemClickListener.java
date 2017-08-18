package com.future.awaker.base.listener;

import android.view.View;

/**
 * Copyright ©2017 by ruzhan
 */

public interface OnItemClickListener<T> {

    void onItemClick(View view, int position, T bean);
}
