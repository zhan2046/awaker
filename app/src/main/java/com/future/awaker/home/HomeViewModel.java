package com.future.awaker.home;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.future.awaker.R;
import com.future.awaker.base.BaseViewModel;
import com.future.awaker.data.Banner;
import com.future.awaker.data.BannerItem;
import com.future.awaker.data.source.repository.NewRepository;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.util.ResUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Copyright ©2017 by Teambition
 */

public class HomeViewModel extends BaseViewModel {

    private String advType	= ResUtils.getString(R.string.adv_type);
    public ObservableList<BannerItem> bannerItems = new ObservableArrayList<>();

    public void getBanner() {
        disposable.add(NewRepository.get().getBanner(TOKEN, advType)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(httpResult -> {
                    checkEmpty(httpResult.getData());
                    bannerItems.addAll(httpResult.getData());
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }
}
