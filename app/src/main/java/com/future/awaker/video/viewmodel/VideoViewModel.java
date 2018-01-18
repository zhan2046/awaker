package com.future.awaker.video.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.future.awaker.base.viewmodel.BaseListViewModel;
import com.future.awaker.data.Special;
import com.future.awaker.data.other.RefreshListModel;
import com.future.awaker.db.entity.SpecialListEntity;
import com.future.awaker.network.EmptyConsumer;
import com.future.awaker.network.ErrorConsumer;
import com.future.awaker.network.HttpResult;
import com.future.awaker.source.AwakerRepository;
import com.future.awaker.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private RefreshListModel<Special> refreshListModel = new RefreshListModel<>();

    private List<Special> specialList = new ArrayList<>();
    private MutableLiveData<RefreshListModel<Special>> specialLiveData = new MutableLiveData<>();

    private Map<String, List<Special>> stringListMap = new HashMap<>();

    private Disposable localDisposable;

    public VideoViewModel() {

    }

    public void loadSpecialListEntity(String cat) {
        localDisposable = AwakerRepository.get().loadSpecialListEntity(cat)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> LogUtils.showLog(TAG,
                        "initLocalSpecialListEntity doOnError: " + throwable.toString()))
                .doOnNext(specialListEntity -> {
                    if (specialListEntity.specialList != null && !stringListMap.containsKey(cat)) {
                        refreshListModel.setRefreshType(specialListEntity.specialList);
                        specialLiveData.setValue(refreshListModel);
                    }
                    localDisposable.dispose();
                })
                .subscribe(new EmptyConsumer(), new ErrorConsumer());
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
            refreshListModel.setRefreshType();

        } else {
            refreshListModel.setUpdateType();
        }
        specialList.addAll(specials);
        refreshListModel.setList(specialList);
        specialLiveData.setValue(refreshListModel);

        stringListMap.put(String.valueOf(cat), specialList);

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

    public MutableLiveData<RefreshListModel<Special>> getSpecialLiveData() {
        return specialLiveData;
    }
}
