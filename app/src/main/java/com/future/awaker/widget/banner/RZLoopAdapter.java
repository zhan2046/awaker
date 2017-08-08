package com.future.awaker.widget.banner;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhan on 2016/12/27.
 */

public abstract class RZLoopAdapter extends PagerAdapter {

    protected static int MAX_ITEM_COUNT = Integer.MAX_VALUE / 4;

    protected RZLoopViewPager mRZLoopViewPager;
    protected SparseArray<View> mViews = new SparseArray<>();
    protected int mRealCount;

    public RZLoopAdapter(int realCount, RZLoopViewPager viewPager) {
        mRealCount = realCount;
        mRZLoopViewPager = viewPager;
    }

    @Override
    public int getCount() {
        return mRealCount > 1 ? MAX_ITEM_COUNT : mRealCount;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public abstract View instantiateItemView(ViewGroup container, int realPosition);

    public abstract void initViewToData(View view, int realPosition);

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = getRealPosition(position);

        View view = mViews.get(realPosition);
        if (view == null) {
            view = instantiateItemView(container, realPosition);
        }

        initViewToData(view, realPosition);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public int getRealPosition(int position) {
        int realPosition = position % mRealCount;
        if (realPosition < 0) {
            realPosition = 0;
        }

        return realPosition;
    }

    public void setRealCount(int realCount) {
        mRealCount = realCount;
    }

    public int getRealCount() {
        return mRealCount;
    }

    public void setMaxItemCount(int maxItemCount) {
        MAX_ITEM_COUNT = maxItemCount;
    }
}
