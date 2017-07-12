package com.future.awaker.util;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Copyright ©2017 by Teambition
 */

public final class AnimatorUtils {

    private static final long REVEAL_DURATION = 800;

    private AnimatorUtils() {
    }

    /**
     * 揭露效果 Animator
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Animator createRevealAnimator(View rootView, int x, int y, boolean reversed,
                                                Animator.AnimatorListener listener) {
        float hypot = (float) Math.hypot(rootView.getMeasuredHeight(), rootView.getMeasuredWidth());
        float startRadius = reversed ? hypot : 0;
        float endRadius = reversed ? 0 : hypot;

        Animator animator = ViewAnimationUtils.createCircularReveal(rootView, x, y,
                startRadius, endRadius);
        animator.setDuration(REVEAL_DURATION);
        if (listener != null) {
            animator.addListener(listener);
        }
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        return animator;
    }
}
