package com.future.awaker.base;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Copyright ©2017 by Teambition
 */

public abstract class IDiffCallBack<T> extends DiffUtil.Callback {

    protected List<T> oldData;
    protected List<T> newData;

    public void setData(List<T> oldData, List<T> newData) {
        this.oldData = oldData;
        this.newData = newData;
    }

    @Override public int getOldListSize() {
        return oldData == null ? 0 : oldData.size();
    }

    @Override public int getNewListSize() {
        return newData == null ? 0 : newData.size();
    }

    public abstract boolean isItemsTheSame(int oldItemPosition, int newItemPosition);

    @Override public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return isItemsTheSame(oldItemPosition, newItemPosition);
    }

    public abstract boolean isContentsTheSame(int oldItemPosition, int newItemPosition);

    @Override public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return isContentsTheSame(oldItemPosition, newItemPosition);
    }
}
