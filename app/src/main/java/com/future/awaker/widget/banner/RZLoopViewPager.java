package com.future.awaker.widget.banner;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * Created by zhan on 2016/12/27.
 */

public class RZLoopViewPager extends ViewPager {

  private static int START_TIME_TASK = 600;
  private static int NORMAL_MULTIPLE = 30;

  private int mNextPosition = Integer.MAX_VALUE / 8;

  private Scroller mScroller;
  private LoopTask mLoopTask;
  private RZLoopAdapter mRZLoopAdapter;
  private OnPageChangeListener mOutPageChangeListener;//out side use listener

  public void addOutPageChangeListener(OnPageChangeListener listener) {
    mOutPageChangeListener = listener;
  }

  public RZLoopViewPager(Context context) {
    super(context);
    init();
  }

  public RZLoopViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  private void init() {
    mLoopTask = new LoopTask(this);
    OnPageChangeListener inPageChangeListener = new RZLoopOnPageChangeListener();
    super.addOnPageChangeListener(inPageChangeListener);

    setScroller();
  }

  public void setScroller() {
    try {
      Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
      scrollerField.setAccessible(true);
      Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
      interpolatorField.setAccessible(true);

      mScroller = new RZDurationScroller(getContext(), (Interpolator) interpolatorField.get(null));
      scrollerField.set(this, mScroller);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setNextPosition(int nextPosition) {
    mNextPosition = nextPosition;
  }

  public int getNextPosition() {
    return mNextPosition;
  }

  @Override public void setAdapter(PagerAdapter adapter) {
    super.setAdapter(adapter);

    if (adapter instanceof RZLoopAdapter) {
      mRZLoopAdapter = (RZLoopAdapter) adapter;
      mNextPosition = mRZLoopAdapter.getRealCount() * NORMAL_MULTIPLE;
    } else {
      throw new RuntimeException("RZLoopViewPager adapter must RZLoopAdapter");
    }
  }

  public RZLoopAdapter getLoopAdapter() {
    return mRZLoopAdapter;
  }

  public void setNormalMultiple(int multiple) {
    NORMAL_MULTIPLE = multiple;
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();

    //remove loop task
    stopLoopPager();
  }

  public void startLoopPager() {
    postDelayed(new Runnable() {
      @Override public void run() {
        if (mLoopTask != null) {
          mLoopTask.startTask();
        }
      }
    }, START_TIME_TASK);
  }

  public void stopLoopPager() {
    mLoopTask.stopTask();
  }

  private static class LoopTask implements Runnable {

    private static int FIRST_TIME_TASK = 300;
    private static int TIME_TASK = 4000;

    private boolean isFirst;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private WeakReference<RZLoopViewPager> mWeakViewPager;

    private boolean isRunningTask;
    private long mStartTaskTime;

    public LoopTask(RZLoopViewPager viewPager) {
      mWeakViewPager = new WeakReference<>(viewPager);
    }

    public void startTask() {
      if (!isRunningTask) {
        isRunningTask = true;
        mHandler.removeCallbacks(this);

        if (!isFirst) {
          isFirst = true;
          RZLoopViewPager loopViewPager = mWeakViewPager.get();
          if (loopViewPager != null) {
            loopViewPager.setCurrentItem(loopViewPager.getNextPosition(), true);
          }
        }

        mHandler.postDelayed(this, FIRST_TIME_TASK);
      }
    }

    public void stopTask() {
      if (isRunningTask) {
        isRunningTask = false;
        mHandler.removeCallbacks(this);

        //reset nextPosition,let next start no next
        RZLoopViewPager loopViewPager = mWeakViewPager.get();
        if (loopViewPager != null) {
          int nextPosition = loopViewPager.getNextPosition();
          nextPosition --;
          loopViewPager.setNextPosition(nextPosition);
        }
      }
    }

    @Override public void run() {

      if (!isRunningTask) {
        return;
      }

      ////keep TIME_TASK execute one loop task
      //long time = System.currentTimeMillis() - mStartTaskTime;
      //if(time < TIME_TASK) {
      //  Log.i("RZLoopViewPager","task out time !!!!");
      //  return;
      //}
      //mStartTaskTime = System.currentTimeMillis();

      //get RZLoopViewPager instance
      RZLoopViewPager loopViewPager = mWeakViewPager.get();
      if (loopViewPager == null) {
        return;
      }

      //view pager to next
      int nextPosition = loopViewPager.getNextPosition();
      loopViewPager.setCurrentItem(nextPosition, true);

      //refresh next position
      nextPosition++;
      loopViewPager.setNextPosition(nextPosition);

      //wait next loop task
      if (isRunningTask) {
        mHandler.postDelayed(this, TIME_TASK);
      }
    }
  }

  private class RZLoopOnPageChangeListener implements OnPageChangeListener {

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      if (mOutPageChangeListener != null) {
        mOutPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
      }
    }

    @Override public void onPageSelected(int position) {
      mNextPosition = position;

      if (mOutPageChangeListener != null) {
        mOutPageChangeListener.onPageSelected(position);
      }
    }

    @Override public void onPageScrollStateChanged(int state) {
      if (mOutPageChangeListener != null) {
        mOutPageChangeListener.onPageScrollStateChanged(state);
      }

      //checkout task is stop status,this restart loop task
      //before to onPageSelected call stop,sometime show no scroll bug...
      //onPageScrollStateChanged call startLoopPager is ok
      startLoopPager();
    }
  }
}

