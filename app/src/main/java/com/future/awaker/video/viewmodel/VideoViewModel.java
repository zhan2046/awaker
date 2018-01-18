package com.future.awaker.video.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.future.awaker.base.viewmodel.BaseListViewModel;
import com.future.awaker.data.Special;
import com.future.awaker.db.entity.SpecialListEntity;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.network.HttpResult;
import com.future.awaker.source.AwakerRepository;
import com.future.awaker.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ruzhan on 2017/7/6.
 */

public class VideoViewModel extends BaseListViewModel {

    private static final String TAG = "VideoViewModel";

    private int cat = Special.NORMAL;
    private List<Special> specialList = new ArrayList<>();
    private MutableLiveData<List<Special>> specialLiveData = new MutableLiveData<>();

    private Disposable localDisposable;

    public VideoViewModel() {

    }

    public void initLocalSpecialListEntity() {
        localDisposable = AwakerRepository.get().loadSpecialListEntity(String.valueOf(cat))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "initLocalSpecialListEntity doOnError: " + throwable.toString()))
                .doOnNext(this::setLocalSpecialListEntity)
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void setLocalSpecialListEntity(SpecialListEntity specialListEntity) {
        if (specialListEntity.specialList != null && specialList.isEmpty()) {
            specialLiveData.setValue(specialListEntity.specialList);
            localDisposable.dispose();
        }
    }

    @Override
    public void refreshData(boolean refresh) {
        AwakerRepository.get().getSpecialList(TOKEN, page, cat)
                .map(HttpResult::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "refreshData doOnError called..." + throwable.toString()))
                .doOnSubscribe(disposable -> isRunning.set(true))
                .doOnTerminate(() -> isRunning.set(false))
                .doOnNext(specials -> refreshDataOnNext(specials, refresh))
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void refreshDataOnNext(List<Special> specials, boolean refresh) {
        if (refresh) {
            specialList.clear();
        }
        specialList.addAll(specials);
        specialLiveData.setValue(specialList);

        setSpecialListToLocalDb(specialList);
    }

    private void setSpecialListToLocalDb(List<Special> specials) {
        Flowable.create(e -> saveSpecialListToLocal(new ArrayList<>(specials), e),
                BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "setSpecialListToLocalDb doOnError: " + throwable.toString()))
                .doOnComplete(() -> LogUtils.showLog(TAG,
                        "setSpecialListToLocalDb doOnComplete called..."))
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
    }

    private void saveSpecialListToLocal(List<Special> specials, FlowableEmitter e) {
        if (specials != null) {
            SpecialListEntity specialListEntity = new SpecialListEntity(String.valueOf(cat), specials);
            AwakerRepository.get().insertAll(specialListEntity);
        }
        e.onComplete();
    }

    public void setCat(int cat) {
        this.cat = cat;
    }

    public MutableLiveData<List<Special>> getSpecialLiveData() {
        return specialLiveData;
    }
}
