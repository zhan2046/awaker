package com.future.awaker.home.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.future.awaker.R;
import com.future.awaker.base.viewmodel.BaseViewModel;
import com.future.awaker.data.BannerItem;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.network.HttpResult;
import com.future.awaker.source.AwakerRepository;
import com.future.awaker.util.LogUtils;
import com.future.awaker.util.ResUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright ©2017 by ruzhan
 */

public class HomeViewModel extends BaseViewModel {

    private static final String TAG = HomeViewModel.class.getSimpleName();

    private String advType = ResUtils.getString(R.string.adv_type);
    private MutableLiveData<List<BannerItem>> bannerLiveData = new MutableLiveData<>();

    public HomeViewModel() {
        bannerLiveData.setValue(null);
    }

    public void initLocalBanners() {
        AwakerRepository.get().loadAllBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "initLocalBanners doOnError: " + throwable.toString()))
                .doOnNext(this::setLocalBanners)
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setLocalBanners(List<BannerItem> banners) {
        if (banners != null && bannerLiveData.getValue() == null) {
            bannerLiveData.setValue(banners);
        }
    }

    public void getBanner() {
        AwakerRepository.get().getBanner(TOKEN, advType)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "getBanner doOnError: " + throwable.toString()))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .map(HttpResult::getData)
                .doOnNext(this::refreshDataOnNext)
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void refreshDataOnNext(List<BannerItem> news) {
        List<BannerItem> bannerList = bannerLiveData.getValue();
        if (bannerList == null) {
            bannerList = new ArrayList<>();
        }
        bannerList.clear();
        bannerList.addAll(news);
        bannerLiveData.setValue(bannerList);

        // save news to local db
        setBannerToLocalDb(bannerList);
    }

    private void setBannerToLocalDb(List<BannerItem> bannerList) {
        Flowable.create(e -> saveBannerToLocal(new ArrayList<>(bannerList), e),
                BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "setBannerToLocalDb doOnError: " + throwable.toString()))
                .doOnComplete(() -> {})
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void saveBannerToLocal(List<BannerItem> bannerList, FlowableEmitter e) {
        if (bannerList != null && !bannerList.isEmpty()) {
            AwakerRepository.get().insertAllBanners(bannerList);
        }
        e.onComplete();
    }

    public MutableLiveData<List<BannerItem>> getBannerLiveData() {
        return bannerLiveData;
    }
}
