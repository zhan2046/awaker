package com.future.awaker.base;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import java.util.List;

/**
 * Copyright ©2017 by Teambition
 */

public abstract class BaseListViewModel extends BaseViewModel {

    protected static final int NORMAL_PAGE = 1;
    protected static final int PAGE_SIZE = 10;

    protected int page;
    protected boolean isRefresh;
    public ObservableBoolean refreshing = new ObservableBoolean(false); // 提供给下拉刷新

    public abstract void refreshData(boolean refresh);

    public void fetchData(boolean refresh) {
        if (refresh) {
            if (isRunning.get()) {
                refreshing.set(false);
                return;
            }
            page = NORMAL_PAGE;

        } else {
            if (isRunning.get() || isEmpty.get()) {
                return;
            }
            page++;
        }

        setRefresh(refresh);
        refreshData(refresh);
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
        if (isRefresh) { // 网络请求开始显示下拉刷新
            refreshing.set(true);
        }
    }

    protected <T> void setDataList(List<T> list, ObservableList<T> observableList) {
        setDataList(isEmpty, list, observableList, isRefresh, PAGE_SIZE);
    }

    protected <T> void setDataList(ObservableBoolean isEmpty, List<T> list,
                                   ObservableList<T> observableList,
                                   boolean isRefresh, int pageSize) {
        if (observableList == null || isEmpty == null) {
            return;
        }
        checkEmpty(list);
        if (!isEmpty.get()) {
            if (isRefresh) {
                observableList.clear();
            }
            observableList.addAll(list);
        }
        if (list != null && list.size() < pageSize) {
            isEmpty.set(true);
        }
    }
}
