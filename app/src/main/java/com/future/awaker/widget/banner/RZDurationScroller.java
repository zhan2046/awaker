package com.future.awaker.widget.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by zhan on 2016/12/28.
 */

public class RZDurationScroller extends Scroller {

  private double scrollFactor = 3;

  public RZDurationScroller(Context context) {
    super(context);
  }

  public RZDurationScroller(Context context, Interpolator interpolator) {
    super(context, interpolator);
  }

  public RZDurationScroller(Context context, Interpolator interpolator, boolean flywheel) {
    super(context, interpolator, flywheel);
  }

  public void setScrollDurationFactor(double scrollFactor) {
    this.scrollFactor = scrollFactor;
  }

  @Override
  public void startScroll(int startX, int startY, int dx, int dy, int duration) {
    super.startScroll(startX, startY, dx, dy, (int)(duration * scrollFactor));
  }

}
