package com.future.awaker.widget.banner;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zhan on 2016/12/27.
 */

public class BannerImageView extends AppCompatImageView {

  private OnBannerClickListener mOnBannerClickListener;

  public BannerImageView(Context context) {
    super(context);
  }

  public BannerImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public BannerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public interface OnBannerClickListener {

    void actionDown();

    void actionUp();
  }

  public void setOnBannerClickListener(OnBannerClickListener listener) {
    mOnBannerClickListener = listener;
  }

  @Override public boolean onTouchEvent(MotionEvent event) {

    if(event.getAction() == MotionEvent.ACTION_DOWN ||
        event.getAction() == MotionEvent.ACTION_CANCEL){

      //loop view pager stop task
      if(mOnBannerClickListener != null) {
        mOnBannerClickListener.actionDown();
      }
    }

    if(event.getAction() == MotionEvent.ACTION_UP){

      //loop view pager start task
      if(mOnBannerClickListener != null) {
        mOnBannerClickListener.actionUp();
      }
    }

    return super.onTouchEvent(event);
  }
}

