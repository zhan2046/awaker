package com.future.awaker.base;

import android.databinding.ObservableList;

/**
 * Copyright ©2017 by Teambition
 */

public abstract class BaseObservableList<T> extends ObservableList.OnListChangedCallback<ObservableList<T>> {

    public abstract void onChangedList(ObservableList<T> sender);

    @Override
    public void onChanged(ObservableList<T> sender) {
        onChangedList(sender);
    }

    @Override
    public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
        onChangedList(sender);
    }

    @Override
    public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
        onChangedList(sender);
    }

    @Override
    public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
        onChangedList(sender);
    }

    @Override
    public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
        onChangedList(sender);
    }
}
