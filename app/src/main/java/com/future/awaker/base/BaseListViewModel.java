package com.future.awaker.base;

/**
 * Copyright ©2017 by Teambition
 */

public abstract class BaseListViewModel extends BaseViewModel {

    public abstract void fetchData(boolean isRefresh, int page);
}
