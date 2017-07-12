package com.future.awaker.base.listener;

import android.view.View;

/**
 * Copyright ©2017 by Teambition
 * <p>
 * A {@linkplain View.OnClickListener click listener} that debounces multiple clicks posted in the
 * same frame. A click on one button disables all buttons for that frame.
 */

public abstract class DebouncingOnClickListener implements View.OnClickListener {

    private static boolean enabled = true;

    private static final Runnable ENABLE_AGAIN = () -> enabled = true;

    @Override
    public void onClick(View v) {
        if (enabled) {
            enabled = false;
            v.post(ENABLE_AGAIN);
            doClick(v);
        }
    }

    public abstract void doClick(View v);
}
