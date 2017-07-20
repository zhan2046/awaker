package com.future.awaker.base;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import java.util.List;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Copyright ©2017 by Teambition
 */

public class BaseViewModel extends BaseObservable {

    protected static final String TOKEN = "f32b30c2a289bfca2c9857ffc5871ac8";

    public ObservableField<Throwable> isError = new ObservableField<>();
    public ObservableBoolean isEmpty = new ObservableBoolean(false);
    public ObservableBoolean isRunning = new ObservableBoolean(false);

    protected CompositeDisposable disposable = new CompositeDisposable();

    protected void checkEmpty(List list) {
        boolean emptyFlag = list == null || list.isEmpty();
        if (emptyFlag != isEmpty.get()) {
            isEmpty.set(emptyFlag);
        }
    }

    protected void checkEmpty(Object object) {
        boolean emptyFlag = object == null;
        if (emptyFlag != isEmpty.get()) {
            isEmpty.set(emptyFlag);
        }
    }

    public void clear() {
        disposable.clear();
    }
}
