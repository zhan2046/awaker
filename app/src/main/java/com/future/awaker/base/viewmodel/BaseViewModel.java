package com.future.awaker.base.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

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

    protected void checkEmpty(Object object) {
        checkEmpty(isEmpty, object);
    }

    protected void checkEmpty(ObservableBoolean isEmpty, Object object) {
        boolean emptyFlag = object == null;
        if (emptyFlag != isEmpty.get()) {
            isEmpty.set(emptyFlag);
        }
    }

    protected void checkEmpty(List list) {
        checkEmpty(isEmpty, list);
    }

    protected void checkEmpty(ObservableBoolean isEmpty, List list) {
        boolean emptyFlag = list == null || list.isEmpty();
        if (emptyFlag != isEmpty.get()) {
            isEmpty.set(emptyFlag);
        }
    }

    protected <T> void setDataObject(T object, ObservableField<T> field) {
        setDataObject(isEmpty, object, field);
    }

    protected <T> void setDataObject(ObservableBoolean isEmpty, T object,
                                     ObservableField<T> field) {
        if (field == null || isEmpty == null) {
            return;
        }
        checkEmpty(isEmpty, object);
        if (!isEmpty.get()) {
            field.set(object);
        }
    }

    protected <T> void setDataList(List<T> list,
                                   ObservableList<T> observableList) {
        setDataList(isEmpty, list, observableList);
    }

    protected <T> void setDataList(ObservableBoolean isEmpty, List<T> list,
                                   ObservableList<T> observableList) {
        if (observableList == null || isEmpty == null) {
            return;
        }
        checkEmpty(isEmpty, list);
        if (!isEmpty.get()) {
            observableList.clear();
            observableList.addAll(list);
        }
    }

    public void clear() {
        disposable.clear();
    }
}
