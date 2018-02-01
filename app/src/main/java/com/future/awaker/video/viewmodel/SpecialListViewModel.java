package com.future.awaker.video.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.future.awaker.base.viewmodel.BaseListViewModel;
import com.future.awaker.data.SpecialDetail;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.source.AwakerRepository;
import com.future.awaker.util.LogUtils;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ruzhan on 2017/7/15.
 */

public class SpecialListViewModel extends BaseListViewModel {

    private static final String TAG = "SpecialListViewModel";

    private String id;

    private SpecialDetail specialDetail;
    private MutableLiveData<SpecialDetail> specialDetailLiveData = new MutableLiveData<>();

    private Disposable localDisposable;

    public SpecialListViewModel(String id) {
        this.id = id;
        specialDetailLiveData.setValue(null);
    }

    public void initLocalSpecialDetail() {
        localDisposable = AwakerRepository.get().loadSpecialDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "initLocalSpecialDetail doOnError: " + throwable.toString()))
                .doOnNext(this::setLocalSpecialDetail)
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setLocalSpecialDetail(SpecialDetail localSpecialDetail) {
        if (localSpecialDetail != null && specialDetail == null) {
            specialDetail = localSpecialDetail;
            specialDetailLiveData.setValue(specialDetail);
        }
        localDisposable.dispose();
    }

    @Override
    public void refreshData(boolean refresh) {
        AwakerRepository.get()
                .getSpecialDetail(TOKEN, id)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> isError.set(throwable))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(result -> setRefreshDataDoOnNext(refresh, result.getData()))
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setRefreshDataDoOnNext(boolean refresh, SpecialDetail remoteSpecialDetail) {
        if (remoteSpecialDetail != null) {
            specialDetail = remoteSpecialDetail;
            specialDetailLiveData.setValue(specialDetail);

            setSpecialDetailToLocalDb(specialDetail);
        }
    }

    private void setSpecialDetailToLocalDb(SpecialDetail specialDetail) {
        Flowable.create(e -> {
            AwakerRepository.get().insertSpecialDetail(specialDetail);
            e.onComplete();

        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "setSpecialDetailToLocalDb doOnError: " + throwable.toString()))
                .doOnComplete(() -> {
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    public MutableLiveData<SpecialDetail> getSpecialDetailLiveData() {
        return specialDetailLiveData;
    }
}
