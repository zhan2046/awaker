package com.future.awaker.home;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.future.awaker.R;
import com.future.awaker.base.BaseViewModel;
import com.future.awaker.data.BannerItem;
import com.future.awaker.data.realm.BannerItemRealm;
import com.future.awaker.data.realm.BannerRealm;
import com.future.awaker.data.source.NewRepository;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.util.LogUtils;
import com.future.awaker.util.ResUtils;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Copyright ©2017 by Teambition
 */

public class HomeViewModel extends BaseViewModel {

    private static final String TAG = HomeViewModel.class.getSimpleName();

    private String advType = ResUtils.getString(R.string.adv_type);
    public ObservableList<BannerItem> bannerItems = new ObservableArrayList<>();
    private HashMap<String, String> map = new HashMap<>();

    public HomeViewModel() {
        map.put(BannerRealm.ID, BannerRealm.ID_VALUE);
    }

    public void getBanner() {
        if (bannerItems.isEmpty()) {
            getLocalBanner();
        }
        getRemoteBanner();
    }

    private void getRemoteBanner() {
        disposable.add(NewRepository.get().getBanner(TOKEN, advType)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(httpResult -> setRemoteBanner(httpResult.getData()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void getLocalBanner() {
        disposable.add(NewRepository.get().getLocalRealm(BannerRealm.class, map)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG, "doOnError: " + throwable.toString()))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(realmResults -> {
                    LogUtils.d("getLocalBanner" + realmResults.size());
                    setLocalBanner(realmResults);
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer()));
    }

    private void setRemoteBanner(List<BannerItem> bannerItemList) {
        checkEmpty(bannerItemList);
        if (!isEmpty.get()) {
            // save to local
            BannerRealm bannerRealm = new BannerRealm();
            RealmList<BannerItemRealm> realmList =
                    BannerRealm.getBannerItemRealmList(bannerItemList);
            bannerRealm.setBanner_id(BannerRealm.ID_VALUE);
            bannerRealm.setBannerItemList(realmList);
            NewRepository.get().updateLocalRealm(bannerRealm);
            setBanner(bannerItemList);
        }

    }

    private void setLocalBanner(RealmResults realmResults) {
        if (realmResults == null || realmResults.isEmpty()) {
            return;
        }
        BannerRealm bannerRealm =
                (BannerRealm) realmResults.get(0);
        if (bannerItems.isEmpty()) {   // data is empty, network not back
            RealmList<BannerItemRealm> realmList = bannerRealm.getBannerItemList();
            List<BannerItem> bannerItemList = BannerRealm.getBannerItemList(realmList);
            setBanner(bannerItemList);
        }
    }

    private void setBanner(List<BannerItem> bannerItemList) {
        checkEmpty(bannerItemList);
        if (!isEmpty.get()) {
            bannerItems.addAll(bannerItemList);
        }
    }

}
